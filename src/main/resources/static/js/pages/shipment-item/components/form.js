import tooltip from "../../../common/tooltip.js";
import createConfirmModal from "../../../common/modal/confirm.js";
import ajax from "../../../common/ajax.js";
import checkbox from "../../../common/checkbox.js";
import toast from "../../../common/toast.js";
import createInventoryLotMultiSelectModal from "../../../domain/inventory/modal/lot-multi-select.js";
import createItemSelectModal from "../../../domain/item/modal/select.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#shipment-item-table');
    const createButton = document.querySelector('#shipment-item-create-button');
    const deleteButton = document.querySelector('#shipment-item-delete-button');

    const confirmModal = createConfirmModal();
    const itemSelectModal = createItemSelectModal();
    const lotMultiSelectModal = createInventoryLotMultiSelectModal();

    const registerModalOpen = (shipmentId) => {
        itemSelectModal.open({
            title: '출고 제품 선택',
            url: '/items/modal/select/stock-list',
            params: {group: 'PRODUCT'},
            onSelect: async (item) => {
                // 로트 관리 -> LOT 선택 목록
                if (item.lotManaged === 'true') {
                    itemSelectModal.close();

                    lotMultiSelectModal.open({
                        title: '로트 선택',
                        url: '/inventories/modal/lot-multi-select',
                        params: {itemId: item.id},
                        onBack: () => {
                            lotMultiSelectModal.close();
                            registerModalOpen(shipmentId);
                        },
                        onRegister: async (selectedIds) => {
                            try {
                                const response = await ajax.post(
                                    `/api/shipments/${shipmentId}/items`,
                                    {itemId: item.id, inventoryIds: selectedIds}
                                );

                                toast.afterReload({
                                    message: response.message
                                });

                                lotMultiSelectModal.close();
                                location.reload();

                            } catch (xhr) {
                                toast.error({
                                    message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
                                });
                            }
                        }
                    });

                    return;
                }

                // 로트 미관리 -> 추가
                try {
                    const response = await ajax.post(
                        `/api/shipments/${shipmentId}/items`,
                        {itemId: item.id}
                    );

                    toast.afterReload({
                        message: response.message
                    });

                    itemSelectModal.close();
                    location.reload();

                } catch (xhr) {
                    toast.error({
                        message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
                    });
                }
            }
        });
    };

    /* 툴팁 */
    tooltip.init();
    checkbox.init(checkboxGroup);

    /* 출고 제품 추가 모달 */
    createButton.addEventListener('click', function (e) {
        const {shipmentId} = e.currentTarget.dataset;
        registerModalOpen(shipmentId);
    });

    /* 출고 제품 삭제 */
    deleteButton.addEventListener('click', async function () {
        const selectedIds = checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {
            toast.error({
                message: '항목을 선택해 주세요.',
            });

            return;
        }

        confirmModal.open({
            title: '삭제 확인',
            content: '정말 삭제하시겠습니까?',
            onConfirm: async () => {
                try {
                    const response = await ajax.delete('/api/shipment-items', selectedIds);

                    toast.afterReload({
                        message: response.message,
                    });

                    location.reload();

                } catch (xhr) {
                    toast.error({
                        message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
                    });
                }
            }
        });
    });

    /* 금액 계산 */
    const calculateAmount = (row) => {
        const quantity = Number(row.querySelector('.shipment-item-quantity').value);
        const unitPrice = Number(row.querySelector('.shipment-item-unit-price').value);
        const amountEl = row.querySelector('.shipment-item-amount');

        const amount = quantity * unitPrice;

        amountEl.textContent = Math.round(amount).toLocaleString('ko-KR');

        calculateTotal();
    }

    /* 총 합계 계산 */
    const calculateTotal = () => {
        let totalAmount = 0;

        document.querySelectorAll('.shipment-item-quantity').forEach(el => {
            const row = el.closest('tr');
            const quantity = Number(el.value) || 0;
            const unitPrice = Number(row.querySelector('.shipment-item-unit-price').value) || 0;

            totalAmount += quantity * unitPrice;
        });

        document.querySelector('.total-amount').textContent =
            Math.round(totalAmount).toLocaleString('ko-KR');
    }

    document.querySelectorAll('.shipment-item-quantity, .shipment-item-unit-price')
        .forEach(el => {
            el.addEventListener('input', (e) => {
                calculateAmount(e.target.closest('tr'));
            });
        });

    calculateTotal();
});
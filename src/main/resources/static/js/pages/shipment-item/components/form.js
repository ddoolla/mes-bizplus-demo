import tooltip from "../../../common/tooltip.js";
import createConfirmModal from "../../../common/modal/confirm.js";
import ajax from "../../../common/ajax.js";
import checkbox from "../../../common/checkbox.js";
import toast from "../../../common/toast.js";
import createItemSelectWithStockModal from "../../../domain/item/modal/item-select.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#shipment-item-table');
    const createButton = document.querySelector('#shipment-item-create-button');
    const deleteButton = document.querySelector('#shipment-item-delete-button');

    const confirmModal = createConfirmModal();
    const itemSelectWithStockModal = createItemSelectWithStockModal();

    /* 툴팁 */
    tooltip.init();
    checkbox.init(checkboxGroup);

    /* 자재 목록 모달 */
    createButton.addEventListener('click', function () {
        itemSelectWithStockModal.open({
            title: '출고 제품 선택',
            url: '/items/modal/select-with-stock',
            params: {group: 'PRODUCT'},
            onSelect: (item) => {

            }
        });
    });

    /* 입고 자재 삭제 */
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
                    const response = await ajax.delete('/api/receipt-items', selectedIds);

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
});
import tooltip from "../../../common/tooltip.js";
import createConfirmModal from "../../../common/modal/confirm.js";
import ajax from "../../../common/ajax.js";
import checkbox from "../../../common/checkbox.js";
import toast from "../../../common/toast.js";
import createItemMultiSelectModal from "../../../domain/item/modal/multi-select.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#receipt-item-table');
    const deleteButton = document.querySelector('#receipt-item-delete-button');
    const createButton = document.querySelector('#receipt-item-create-button');

    const confirmModal = createConfirmModal();
    const itemMultiSelectModal = createItemMultiSelectModal();

    /* 툴팁 */
    tooltip.init();
    checkbox.init(checkboxGroup);

    /* 자재 목록 모달 */
    createButton.addEventListener('click', function () {
        itemMultiSelectModal.open({
            title: '자재 목록',
            url: '/items/modal/multi-select/stock-list',
            params: {group: 'MATERIAL'},
            onRegister: async (selectedIds) => {
                const {receiptId} = createButton.dataset;

                try {
                    const response = await ajax.post(
                        `/api/receipts/${receiptId}/items`,
                        {itemIds: selectedIds},
                    );

                    itemMultiSelectModal.close();

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

    /* 금액 계산 */
    const calculateAmount = (row) => {
        const quantity = Number(row.querySelector('.receipt-item-quantity').value);
        const unitPrice = Number(row.querySelector('.receipt-item-unit-price').value);
        const amountEl = row.querySelector('.receipt-item-amount');

        const amount = quantity * unitPrice;

        amountEl.textContent = Math.round(amount).toLocaleString('ko-KR');

        calculateTotal();
    }

    /* 총 합계 계산 */
    const calculateTotal = () => {
        let totalAmount = 0;

        document.querySelectorAll('.receipt-item-quantity').forEach(el => {
            const row = el.closest('tr');
            const quantity = Number(el.value) || 0;
            const unitPrice = Number(row.querySelector('.receipt-item-unit-price').value) || 0;

            totalAmount += quantity * unitPrice;
        });

        document.querySelector('.total-amount').textContent =
            Math.round(totalAmount).toLocaleString('ko-KR');
    }

    document.querySelectorAll('.receipt-item-quantity, .receipt-item-unit-price')
        .forEach(el => {
            el.addEventListener('input', (e) => {
                calculateAmount(e.target.closest('tr'));
            });
        });

    calculateTotal();
});
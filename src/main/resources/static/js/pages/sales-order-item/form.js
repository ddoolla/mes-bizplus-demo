import ajax from "../../common/ajax.js";
import checkbox from "../../common/checkbox.js";
import toast from "../../common/toast.js";
import createConfirmModal from "../../common/modal/confirm.js";
import createItemMultiSelectModal from "../../domain/item/modal/multi-select.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#sales-order-item-table');
    const deleteButton = document.querySelector('#sales-order-item-delete-button');
    const salesOrderEditForm = document.querySelector('#sales-order-edit-form');
    const createButton = document.querySelector('#sales-order-item-create-button');

    const confirmModal = createConfirmModal();
    const itemMultiSelectModal = createItemMultiSelectModal();

    checkbox.init(checkboxGroup);

    /* 수주 품목 등록 */
    if (createButton) {
        createButton.addEventListener('click', function () {
            itemMultiSelectModal.open({
                title: '제품 목록',
                url: `/items/modal/multi-select/stock-list`,
                params: {group: 'PRODUCT'},
                onRegister: async (selectedIds) => {
                    const salesOrderId = salesOrderEditForm.querySelector('[name="id"]').value;

                    try {
                        const response = await ajax.post(
                            `/sales-orders/${salesOrderId}/items`,
                            {itemIds: selectedIds}
                        );

                        itemMultiSelectModal.close();

                        toast.afterReload({
                            message: response.message,
                        });

                        location.reload();

                    } catch (xhr) {
                        toast.error({
                            message: xhr.responseJSON.message,
                        });
                    }
                }
            });
        });
    }

    /* 수주 품목 삭제 */
    if (deleteButton) {
        deleteButton.addEventListener('click', async function () {
            const selectedIds = checkbox.getCheckedValues(checkboxGroup);

            if (!selectedIds.length) {
                toast.error({
                    message: '항목을 선택해 주세요.'
                });

                return;
            }

            confirmModal.open({
                title: '삭제 확인',
                content: '정말 삭제하시겠습니까?',
                onConfirm: async () => {
                    try {
                        const response = await ajax.delete('/sales-order-items', selectedIds);

                        toast.afterReload({
                            message: response.message,
                        });

                        location.reload();

                    } catch (xhr) {
                        toast.error({
                            message: xhr.responseJSON.message,
                        });
                    }
                }
            });
        });
    }

    /* 금액 계산 */
    const calculateAmount = (row) => {
        const quantity = Number(row.querySelector('.sales-order-item-quantity').value);
        const unitPrice = Number(row.querySelector('.sales-order-item-unit-price').value);
        const amountEl = row.querySelector('.sales-order-item-amount');

        const amount = quantity * unitPrice;

        amountEl.textContent = Math.round(amount).toLocaleString('ko-KR');

        calculateTotal();
    }

    /* 총 합계 계산 */
    const calculateTotal = () => {
        let totalAmount = 0;

        document.querySelectorAll('.sales-order-item-quantity').forEach(el => {
            const row = el.closest('tr');
            const quantity = Number(el.value) || 0;
            const unitPrice = Number(row.querySelector('.sales-order-item-unit-price').value) || 0;

            totalAmount += quantity * unitPrice;
        });

        document.querySelector('.total-amount').textContent =
            Math.round(totalAmount).toLocaleString('ko-KR');
    }

    document.querySelectorAll('.sales-order-item-quantity, .sales-order-item-unit-price')
        .forEach(el => {
            el.addEventListener('input', (e) => {
                calculateAmount(e.target.closest('tr'));
            });
        });

    calculateTotal();
});
import tooltip from "../../../common/tooltip.js";
import createConfirmModal from "../../../common/modal/confirm.js";
import createItemMultipleListModal from "../../../domain/item/modal/list/multiple.js";
import ajax from "../../../common/ajax.js";
import checkbox from "../../../common/checkbox.js";
import toast from "../../../common/toast.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#receipt-item-table');
    const deleteButton = document.querySelector('#receipt-item-delete-button');
    const createButton = document.querySelector('#receipt-item-create-button');

    const confirmModal = createConfirmModal();
    const itemMultipleListModal = createItemMultipleListModal();

    /* 툴팁 */
    tooltip.init();
    checkbox.init(checkboxGroup);

    /* 자재 목록 모달 */
    createButton.addEventListener('click', function () {
        itemMultipleListModal.open({
            title: '자재 목록',
            url: '/items/modal/list/multiple',
            params: {group: 'MATERIAL'},
            onRegister: async (selectedIds) => {
                const {receiptId} = createButton.dataset;

                try {
                    const response = await ajax.post(
                        `/api/receipts/${receiptId}/items`,
                        {itemIds: selectedIds},
                    );

                    itemMultipleListModal.close();

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
});
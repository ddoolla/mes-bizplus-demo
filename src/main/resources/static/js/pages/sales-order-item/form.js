import ajax from "../../common/ajax.js";
import checkbox from "../../common/checkbox.js";
import createItemMultipleListModal from "../../domain/item/modal/list/multiple.js";
import toast from "../../common/toast.js";
import createConfirmModal from "../../common/modal/confirm.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#sales-order-item-table');
    const deleteButton = document.querySelector('#sales-order-item-delete-button');
    const salesOrderEditForm = document.querySelector('#sales-order-edit-form');
    const createButton = document.querySelector('#sales-order-item-create-button');

    const confirmModal = createConfirmModal();
    const itemMultipleListModal = createItemMultipleListModal();

    checkbox.init(checkboxGroup);

    /* 수주 품목 등록 */
    createButton.addEventListener('click', function () {
        itemMultipleListModal.open({
            title: '제품 목록',
            url: `/items/modal/list/multiple`,
            params: {group: 'PRODUCT'}
        });
    });

    itemMultipleListModal.onRegister(async function (selectedIds) {
        const salesOrderId = salesOrderEditForm.querySelector('[name="id"]').value;

        try {
            const response = await ajax.post(
                `/sales-orders/${salesOrderId}/items`,
                {itemIds: selectedIds}
            );

            itemMultipleListModal.close();

            toast.afterReload({
                message: response.message,
            });

            location.reload();

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON.message,
            });
        }
    });

    /* 수주 품목 삭제 */
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
});
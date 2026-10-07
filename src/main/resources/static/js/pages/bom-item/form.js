import ajax from "../../common/ajax.js";
import checkbox from "../../common/checkbox.js";
import createItemMultipleListModal from "../../domain/item/modal/list/multiple.js";
import toast from "../../common/toast.js";
import createConfirmModal from "../../common/modal/confirm.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#bom-item-table');
    const deleteButton = document.querySelector('#bom-item-delete-button');
    const bomEditForm = document.querySelector('#bom-edit-form');
    const itemMultiSelectModalButton = document.querySelector('#bom-item-create-button');

    const confirmModal = createConfirmModal();
    const itemMultipleListModal = createItemMultipleListModal();

    checkbox.init(checkboxGroup);

    // BOM 구성 품목 등록
    itemMultiSelectModalButton.addEventListener('click', function () {
        itemMultipleListModal.open({
            title: 'BOM 구성품 목록',
            url: `/items/modal/list/multiple`,
            params: {group: 'BOM_ITEM'},
            onRegister: async (selectedIds) => {
                const bomId = bomEditForm.querySelector('[name="id"]').value;

                try {
                    const response = await ajax.post(
                        `/boms/${bomId}/items`,
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
            }
        });
    });

    // BOM 구성 품목 삭제
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
                    const response = await ajax.delete('/bom-items', selectedIds);

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
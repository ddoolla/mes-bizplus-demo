import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createConfirmModal from "../../common/modal/confirm.js";
import toast from "../../common/toast.js";
import createCodeNewFormModal from "../../domain/common-code/modal/form/new.js";
import createCodeEditFormModal from "../../domain/common-code/modal/form/edit.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#code-table');
    const createButton = document.querySelector('#code-create-button');
    const editLinks = document.querySelectorAll('.code-edit-link');
    const deleteButton = document.querySelector('#code-delete-button');

    const confirmModal = createConfirmModal();
    const codeNewFormModal = createCodeNewFormModal();
    const codeEditFormModal = createCodeEditFormModal();

    checkbox.init(checkboxGroup);

    /* 코드 등록 */
    createButton.addEventListener('click', function (e) {
       const {groupId} = e.currentTarget.dataset;

       codeNewFormModal.open({
           url: `/code-groups/${groupId}/codes/modal/form/new`,
       });
    });

    /* 코드 수정 */
    editLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            const {groupId, id} = e.currentTarget.dataset;

            codeEditFormModal.open({
                url: `/code-groups/${groupId}/codes/${id}/modal/form/edit`,
            });
        });
    });

    /* 코드 삭제 */
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
                    const response = await ajax.delete('/common-codes', selectedIds);

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
import ajax from "../../common/ajax.js";
import checkbox from "../../common/checkbox.js";
import createProcessMultipleListModal from "../../domain/process/modal/list/multiple.js";
import toast from "../../common/toast.js";
import createConfirmModal from "../../common/modal/confirm.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#worker-process-table');
    const deleteButton = document.querySelector('#worker-process-delete-button');
    const createButton = document.querySelector('#worker-process-create-button');

    const confirmModal = createConfirmModal();
    const processMultipleListModal = createProcessMultipleListModal();

    checkbox.init(checkboxGroup);

    /* 담당 공정 추가 */
    createButton.addEventListener('click', function (e) {
        processMultipleListModal.open({
            title: '공정 목록',
            url: '/processes/modal/list/multiple',
            onRegister: async (selectedIds) => {
                const {workerId} = createButton.dataset;

                try {
                    const response = await ajax.post(
                        `/workers/${workerId}/processes`,
                        {processIds: selectedIds});

                    processMultipleListModal.close();

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

    /* 담당 공정 목록 삭제 */
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
                    const response = await ajax.delete('/workers/processes', selectedIds);

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
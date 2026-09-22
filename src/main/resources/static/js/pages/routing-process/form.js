import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createProcessMultipleListModal from "../../domain/process/modal/list/multiple.js";
import toast from "../../common/toast.js";
import createConfirmModal from "../../common/modal/confirm.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#routing-process-table');
    const deleteButton = document.querySelector('#routing-process-delete-button');
    const createButton = document.querySelector('#routing-process-create-button');

    const confirmModal = createConfirmModal();
    const processMultipleListModal = createProcessMultipleListModal();

    // 제품 공정 단계 등록
    createButton.addEventListener('click', function () {
        processMultipleListModal.open('공정 목록');
    });

    processMultipleListModal.onRegister(async function (selectedIds) {
        const routingId = createButton.dataset.routingId;

        try {
            const response = await ajax.post(
                `/routings/${routingId}/processes`,
                {processIds: selectedIds}
            );

            processMultipleListModal.close();

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

    // 제품 공정 단계 삭제
    checkbox.init(checkboxGroup);

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
                    const response = await ajax.delete('/routing-processes', selectedIds);

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
import ajax from "../../common/ajax.js";
import checkbox from "../../common/checkbox.js";
import createInspectionItemMultipleListModal from "../../domain/inspection-item/modal/list/multiple.js";
import toast from "../../common/toast.js";
import createConfirmModal from "../../common/modal/confirm.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#inspection-spec-item-table');
    const deleteButton = document.querySelector('#inspection-spec-item-delete-button');
    const createButton = document.querySelector('#inspection-spec-item-create-button');

    const confirmModal = createConfirmModal();
    const inspectionItemMultipleListModal = createInspectionItemMultipleListModal();

    /* 검사항목 목록 모달 */
    createButton.addEventListener('click', function () {
        inspectionItemMultipleListModal.open({
            title: '검사항목 목록',
            url: '/inspection-items/modal/list/multiple',
            onRegister: async (selectedIds) => {
                const {inspectionSpecId} = createButton.dataset;

                try {
                    const response = await ajax.post(
                        `/inspection-specs/${inspectionSpecId}/items`,
                        {inspectionItemIds: selectedIds}
                    );

                    inspectionItemMultipleListModal.close();

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

    /* 검사 항목 삭제 */
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
                    const response = await ajax.delete('/inspection-spec-items', selectedIds);

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
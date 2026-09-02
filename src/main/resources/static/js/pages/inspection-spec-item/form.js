import ajax from "../../common/ajax.js";
import checkbox from "../../common/checkbox.js";
import createInspectionItemMultipleListModal from "../../domain/inspection-item/modal/list/multiple.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#inspection-spec-item-table');
    const deleteButton = document.querySelector('#inspection-spec-item-delete-button');
    const createButton = document.querySelector('#inspection-spec-item-create-button');

    const inspectionItemMultipleListModal = createInspectionItemMultipleListModal();

    /* 검사항목 목록 모달 */
    createButton.addEventListener('click', function () {
        inspectionItemMultipleListModal.open({
            title: '검사항목 목록',
            url: '/inspection-items/modal/list/multiple',
        });
    });

    inspectionItemMultipleListModal.onRegister(async (selectedIds) => {
        const {inspectionSpecId} = createButton.dataset;

        try {
            const response = await ajax.post(
                `/inspection-specs/${inspectionSpecId}/items`,
                {inspectionItemIds: selectedIds}
            );

            alert(response.message);

            location.reload();

            inspectionItemMultipleListModal.close();

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    });

    /* 검사 항목 삭제 */
    checkbox.init(checkboxGroup);

    deleteButton.addEventListener('click', async function () {
        const selectedIds = checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {
            alert('항목을 선택해 주세요.');
            return;
        }

        if (!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        deleteButton.disabled = true;

        try {
            const response = await ajax.delete('/inspection-spec-items', selectedIds);

            alert(response.message);

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    });
});
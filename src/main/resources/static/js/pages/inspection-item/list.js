import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createInspectionItemNewFormModal from "../../domain/inspection-item/modal/form/new.js";
import createInspectionItemEditFormModal from "../../domain/inspection-item/modal/form/edit.js";

document.addEventListener('DOMContentLoaded', function () {
    const checkboxGroup = document.querySelector('#inspection-item-table');
    const deleteButton = document.querySelector('#inspection-item-delete-button');
    const createButton = document.querySelector('#inspection-item-create-button');
    const editLinks = document.querySelectorAll('.inspection-item-edit-link');

    const inspectionItemNewFormModal = createInspectionItemNewFormModal();
    const inspectionItemEditFormModal = createInspectionItemEditFormModal();

    /* 검사항목 등록 */
    createButton.addEventListener('click', function () {
        inspectionItemNewFormModal.open();
    });

    /* 검사항목 수정 */
    editLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            const {id} = e.currentTarget.dataset;

            inspectionItemEditFormModal.open(id);
        });
    });

    /* 검사항목 삭제 */
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
            const response = await ajax.delete('/inspection-items', selectedIds);

            alert(response.message);

            location.reload();
        } catch (xhr) {
            alert(xhr.responseJSON.message);

            location.reload();
        }
    });
});
import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createDefectItemNewFormModal from "../../domain/defect-item/modal/form/new.js";
import createDefectItemEditFormModal from "../../domain/defect-item/modal/form/edit.js";

document.addEventListener('DOMContentLoaded', function () {
    const checkboxGroup = document.querySelector('#defect-item-table');
    const deleteButton = document.querySelector('#defect-item-delete-button');
    const createButton = document.querySelector('#defect-item-create-button');
    const editLinks = document.querySelectorAll('.defect-item-edit-link');

    const defectItemNewFormModal = createDefectItemNewFormModal();
    const defectItemEditFormModal = createDefectItemEditFormModal();

    /* 불량항목 등록 */
    createButton.addEventListener('click', function () {
        defectItemNewFormModal.open();
    });

    /* 불량항목 수정 */
    editLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            const {id} = e.currentTarget.dataset;

            defectItemEditFormModal.open(id);
        });
    });

    /* 불량항목 삭제 */
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
            const response = await ajax.delete('/defect-items', selectedIds);

            alert(response.message);

            location.reload();
        } catch (xhr) {
            alert(xhr.responseJSON.message);

            location.reload();
        }
    });
});
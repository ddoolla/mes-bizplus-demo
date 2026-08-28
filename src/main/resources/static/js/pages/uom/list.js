import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createUomCreateModal from "../../domain/uom/modal/form/create.js";
import createUomEditModal from "../../domain/uom/modal/form/edit.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#uom-table');
    const deleteButton = document.querySelector('#uom-delete-button');
    const createButton = document.querySelector('#uom-create-button');
    const editLinks = document.querySelectorAll('.uom-edit-link');

    checkbox.init(checkboxGroup);
    const uomCreateModal = createUomCreateModal();
    const uomEditModal = createUomEditModal();

    /* 단위 등록 모달 */
    createButton.addEventListener('click', function () {
        uomCreateModal.open();
    });

    /* 단위 수정 모달 */
    editLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            const {id} = e.currentTarget.dataset;

            uomEditModal.open(id);
        });
    });

    /* 단위 삭제 */
    const deleteContacts = async () => {
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
            const response = await ajax.delete('/uoms', selectedIds);

            alert(response.message);

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    };

    deleteButton.addEventListener('click', deleteContacts);
});
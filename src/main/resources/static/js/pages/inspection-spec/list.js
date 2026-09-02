import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";

document.addEventListener('DOMContentLoaded', function () {
    const checkboxGroup = document.querySelector('#inspection-spec-table');
    const deleteButton = document.querySelector('#inspection-spec-delete-button');

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
            const response = await ajax.delete('/inspection-specs', selectedIds);

            alert(response.message);

            location.reload();
        } catch (xhr) {
            alert(xhr.responseJSON.message);

            location.reload();
        }
    });
});
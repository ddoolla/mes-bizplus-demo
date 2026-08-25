import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#code-table');
    const deleteButton = document.querySelector('#code-delete-button');

    checkbox.init(checkboxGroup);

    // 코드 삭제
    deleteButton.addEventListener('click', async function () {

        const selectedIds = checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {
            alert('항목을 선택해 주세요.');
            return;
        }

        if (!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        try {
            const response = await ajax.delete('/common-codes', selectedIds);

            alert(response.message);

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    });
});
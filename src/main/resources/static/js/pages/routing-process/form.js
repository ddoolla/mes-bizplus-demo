import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createProcessMultipleListModal from "../../domain/process/modal/list/multiple.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#routing-process-table');
    const deleteButton = document.querySelector('#routing-process-delete-button');
    const createButton = document.querySelector('#routing-process-create-button');

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

            alert(response.message);

            location.reload();

            processMultipleListModal.close();

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    });

    // 제품 공정 단계 삭제
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
            const response = await ajax.delete('/routing-processes', selectedIds);

            alert(response.message);

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    });
});
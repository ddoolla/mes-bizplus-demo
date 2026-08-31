import ajax from "../../common/ajax.js";
import checkbox from "../../common/checkbox.js";
import createProcessMultipleListModal from "../../domain/process/modal/list/multiple.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#worker-process-table');
    const deleteButton = document.querySelector('#worker-process-delete-button');
    const createButton = document.querySelector('#worker-process-create-button');

    const processMultipleListModal = createProcessMultipleListModal();

    /* 담당 공정 추가 */
    createButton.addEventListener('click', function (e) {
       processMultipleListModal.open('공정 목록');
    });

    processMultipleListModal.onRegister(async (selectedIds) => {
        const {workerId} = createButton.dataset;

        try {
            const response = await ajax.post(
                `/workers/${workerId}/processes`,
                {processIds: selectedIds});

            alert(response.message);

            processMultipleListModal.close();

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
        }
    });

    /* 담당 공정 목록 삭제 */
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
            const response = await ajax.delete('/workers/processes', selectedIds);

            alert(response.message);

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
        }
    });
});
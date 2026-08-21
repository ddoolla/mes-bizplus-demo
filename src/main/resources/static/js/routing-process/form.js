document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#routing-process-table');
    const deleteButton = document.querySelector('#routing-process-delete-button');
    const createButton = document.querySelector('#routing-process-create-button');

    const processMultiSelectModal = createProcessMultiSelectModal();

    // 제품 공정 단계 등록
    createButton.addEventListener('click', function () {
        processMultiSelectModal.open('공정 목록');
    });

    processMultiSelectModal.onRegister(function (selectedIds) {
        const routingId = createButton.dataset.routingId;

        Mes.Ajax.post(`/routings/${routingId}/processes`, {processIds: selectedIds})
            .done(function (response) {
                alert(response.message);

                location.reload();

                processMultiSelectModal.close();
            })
            .fail(function (xhr) {
                alert(xhr.responseJSON.message);
            });
    });

    // 제품 공정 단계 삭제
    Mes.Checkbox.init(checkboxGroup);

    deleteButton.addEventListener('click', function () {

        const selectedIds = Mes.Checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {

            alert('항목을 선택해 주세요.');
            return;
        }

        if (!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        deleteButton.disabled = true;

        Mes.Ajax.delete('/routing-processes', selectedIds)
            .done(function (response) {

                alert(response.message);

                location.reload();
            })
            .fail(function (xhr) {

                alert(xhr.responseJSON.message);

                location.reload();
            });
    });
});
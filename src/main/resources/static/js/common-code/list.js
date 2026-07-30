document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#code-list');
    const deleteButton = document.querySelector('#delete-code-button');

    Mes.Checkbox.init(checkboxGroup);

    // 코드 삭제
    deleteButton.addEventListener('click', function () {

        const selectedIds = Mes.Checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {
            alert('항목을 선택해 주세요.');
            return;
        }

        if (!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        Mes.Ajax.delete('/common-codes', selectedIds)
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
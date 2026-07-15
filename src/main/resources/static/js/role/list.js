document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#role-list');
    const deleteButton = document.querySelector('#delete-role-button');

    Checkbox.init(checkboxGroup);

    deleteButton.addEventListener('click', function () {

        const selectedIds = Checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {

            alert('항목을 선택해 주세요.');
            return;
        }

        if (!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        deleteButton.disabled = true;

        Ajax.delete('/roles', selectedIds)
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
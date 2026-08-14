document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#partner-contact-table');
    const deleteButton = document.querySelector('#contact-delete-button');

    Mes.Checkbox.init(checkboxGroup);

    const deleteContacts = () => {

        const selectedIds = Mes.Checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {

            alert('항목을 선택해 주세요.');
            return;
        }

        if (!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        deleteButton.disabled = true;

        Mes.Ajax.delete('/partner-contacts', selectedIds)
            .done(function (response) {

                alert(response.message);

                location.reload();
            })
            .fail(function (xhr) {

                alert(xhr.responseJSON.message);

                location.reload();
            });
    }

    deleteButton.addEventListener('click', deleteContacts);
});
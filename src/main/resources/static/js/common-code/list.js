document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#code-list');
    const deleteButton = document.querySelector('#delete-code-button');

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

     /* 코드 수정 모달 */
    const editModal = document.getElementById("edit-code-modal");

    if (editModal) {

        editModal.addEventListener('show.bs.modal', async event => {

            const link = event.relatedTarget;

            const groupId = link.getAttribute('data-bs-group-id');
            const id = link.getAttribute('data-bs-id');

            const commonCode = await Ajax.get(`/common-codes/${id}`);

            document.querySelector('#edit-code-form').action = `/code-groups/${groupId}/codes/${id}`;

            document.querySelector('#id').value = commonCode.id;
            document.querySelector('#edit-code').value = commonCode.code;
            document.querySelector('#edit-name').value = commonCode.name;
            document.querySelector('#edit-description').value = commonCode.description;
        });
    }
});
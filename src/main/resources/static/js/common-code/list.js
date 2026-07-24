document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#code-list');
    const deleteButton = document.querySelector('#delete-code-button');

    Mes.Checkbox.init(checkboxGroup);

    // 수정 모달 열기
    const openEditModal = async (event) => {

        event.preventDefault();

        const link = event.currentTarget;

        const groupId = link.dataset.groupId;
        const id = link.dataset.id;

        const commonCode = await Mes.Ajax.get(`/common-codes/${id}`);

        document.querySelector('#edit-code-form').action = `/code-groups/${groupId}/codes/${id}`;

        document.querySelector('#id').value = commonCode.id;
        document.querySelector('#edit-code').value = commonCode.code;
        document.querySelector('#edit-name').value = commonCode.name;
        document.querySelector('#edit-description').value = commonCode.description;

        Mes.Modal.open('edit-code-modal');
    }

    document.querySelectorAll('.edit-code-link')
        .forEach(link => link.addEventListener('click', openEditModal));

    // 코드 삭제
    const deleteCodes = () => {

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
    }

    deleteButton.addEventListener('click', deleteCodes);
});
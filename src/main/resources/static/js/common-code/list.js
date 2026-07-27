document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#code-list');
    const deleteButton = document.querySelector('#delete-code-button');

    Mes.Checkbox.init(checkboxGroup);

    // 수정 모달 열기
    document.querySelectorAll('.edit-code-link').forEach(link =>
        link.addEventListener('click', async (event) => {

                event.preventDefault();

                const link = event.currentTarget;

                const groupId = link.dataset.groupId;
                const id = link.dataset.id;

                const commonCode = await Mes.Ajax.get(`/common-codes/${id}`);

                const modal = document.querySelector('#edit-code-modal');
                const form = modal.querySelector('form');

                form.action = `/code-groups/${groupId}/codes/${id}`;

                Mes.Form.set(form, {
                    id: commonCode.id,
                    code: commonCode.code,
                    name: commonCode.name,
                    description: commonCode.description,
                });

                Mes.Modal.open('edit-code-modal');
            }
        ));

    // 모달 닫기 시 폼 초기화
    Mes.Modal.resetFormOnHidden('create-code-modal');
    Mes.Modal.resetFormOnHidden('edit-code-modal');

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
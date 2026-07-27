document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#partner-contact-list');

    Mes.Checkbox.init(checkboxGroup);

    // 수정 모달 열기
    document.querySelectorAll('.edit-contact-link')
        .forEach(link => {
            link.addEventListener('click', async (event) => {

                event.preventDefault();

                const id = link.dataset.id;

                const contact = await Mes.Ajax.get(`/partner-contacts/${id}`);

                const modal = document.querySelector('#edit-contact-modal');
                const form = modal.querySelector('form');

                form.action = `/partner-contacts/${id}`;

                Mes.Form.set(form, {
                    name: contact.name,
                    departmentId: contact.department?.id,
                    positionId: contact.position?.id,
                    phone: contact.phone,
                    tel: contact.tel,
                    email: contact.email,
                    remark: contact.remark,
                    active: contact.active,
                });

                Mes.Modal.open('edit-contact-modal');
            });
        });

    // 모달 닫기 시 폼 초기화
    Mes.Modal.resetFormOnHidden('create-contact-modal');
    Mes.Modal.resetFormOnHidden('edit-contact-modal');

    // 담당자 삭제
    document.querySelector('#delete-contact-button')
        .addEventListener('click', function () {

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
        });
});
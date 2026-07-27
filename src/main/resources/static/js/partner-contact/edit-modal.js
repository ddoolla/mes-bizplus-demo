document.addEventListener('DOMContentLoaded', function () {

    $('#edit-contact-form').validate({
        rules: {
            name: 'required',
            email: {
                email: true,
            },
            phone: {
                digits: true,
                rangelength: [11, 11],
            },
        },
        messages: {
            name: '담당자명을 입력해 주세요.',
            email: {
                email: '올바른 이메일 형식이 아닙니다.',
            },
            phone: {
                digits: '숫자만 입력해 주세요.',
                rangelength: '휴대폰 번호는 11자리로 입력해 주세요.',
            },
        },
        submitHandler: function (form) {

            const data = {
                name: $(form).find('[name="name"]').val(),
                departmentId: $(form).find('[name="departmentId"]').val(),
                positionId: $(form).find('[name="positionId"]').val(),
                phone: $(form).find('[name="phone"]').val(),
                tel: $(form).find('[name="tel"]').val(),
                email: $(form).find('[name="email"]').val(),
                remark: $(form).find('[name="remark"]').val(),
                active: $(form).find('[name="active"]').val(),
            };

            Mes.Ajax.put(form.action, data)
                .done(function (response) {

                    alert(response.message);

                    Mes.Modal.close('edit-contact-modal');

                    location.reload();
                })
                .fail(function (xhr) {

                    alert(xhr.responseJSON.message);
                });

            return false;
        }
    });
});
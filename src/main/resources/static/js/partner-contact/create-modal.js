document.addEventListener('DOMContentLoaded', function () {

    $('#create-contact-form').validate({
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
                name: form.elements.name.value,
                departmentId: form.elements.departmentId.value,
                positionId: form.elements.positionId.value,
                phone: form.elements.phone.value,
                tel: form.elements.tel.value,
                email: form.elements.email.value,
                remark: form.elements.remark.value,
            };

            Mes.Ajax.post(form.action, data)
                .done(function (response) {

                    alert(response.message);

                    Mes.Modal.close('create-contact-modal');

                    location.reload();
                })
                .fail(function (xhr) {

                    alert(xhr.responseJSON.message);
                });

            return false;
        }
    });
});
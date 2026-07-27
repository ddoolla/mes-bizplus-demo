document.addEventListener('DOMContentLoaded', function () {

    $('#create-code-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/common-codes/check-code',
                    type: 'get',
                    data: {
                        groupId: function () {
                            return $('#group-id').val();
                        }
                    }
                },
            },
            name: 'required',
        },
        messages: {
            code: {
                required: '코드를 입력해 주세요.',
                remote: '이미 존재하는 코드 입니다.',
            },
            name: '코드명을 입력해 주세요.',
        },
        submitHandler: function (form) {

            const data = {
                code: $(form).find('[name="code"]').val(),
                name: $(form).find('[name="name"]').val(),
                description: $(form).find('[name="description"]').val(),
            };

            Mes.Ajax.post(form.action, data)
                .done(function (response) {

                    alert(response.message);

                    Mes.Modal.close('create-code-modal');

                    location.reload();
                })
                .fail(function (xhr) {

                    alert(xhr.responseJSON.message);
                });

            return false;
        }
    });
});
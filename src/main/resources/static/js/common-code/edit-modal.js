document.addEventListener('DOMContentLoaded', function () {

    $('#edit-code-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/common-codes/check-code',
                    type: 'get',
                    data: {
                        groupId: function () {
                            return $('#group-id').val();
                        },
                        id: function () {
                            return $('#id').val();
                        },
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
                code: $('#edit-code').val(),
                name: $('#edit-name').val(),
                description: $('#edit-description').val()
            };

            Mes.Ajax.put(form.action, data)
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
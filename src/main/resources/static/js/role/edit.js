document.addEventListener('DOMContentLoaded', function () {

    $('#edit-role-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/roles/check-code',
                    type: 'get',
                    data: {
                        id: function () {
                            return $('#id').val();
                        }
                    }
                },
            },
            name: 'required',
        }, messages: {
            code: {
                required: '권한 코드를 입력해 주세요.',
                remote: '이미 존재하는 권한 코드 입니다.',
            },
            name: '권한 이름을 입력해 주세요.',
        }
    });
});
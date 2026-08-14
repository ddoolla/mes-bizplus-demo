document.addEventListener('DOMContentLoaded', function () {

    $('#user-create-form').validate({
        rules: {
            userId: {
                required: true,
                remote: {
                    url: '/users/check-id',
                    type: 'get',
                },
            },
            password: {
                required: true,
                minlength: 4,
            },
            passwordConfirm: {
                required: true,
                equalTo: '#password'
            },
            name: 'required',
            email: {
                email: true,
            },
            phone: {
                digits: true,
                rangelength: [11, 11],
            },
        }, messages: {
            userId: {
                required: '사용자 ID를 입력해 주세요.',
                remote: '이미 존재하는 ID 입니다.',
            },
            password: {
                required: '비밀번호를 입력해 주세요.',
                minlength: '4자리 이상 입력해 주세요.',
            },
            passwordConfirm: {
                required: '비밀번호를 입력해 주세요.',
                equalTo: '비밀번호가 일치하지 않습니다.'
            },
            name: '사용자 이름을 입력해 주세요.',
            email: {
                email: '올바른 이메일 형식이 아닙니다.',
            },
            phone: {
                digits: '숫자만 입력해 주세요.',
                rangelength: '휴대폰 번호는 11자리로 입력해 주세요.',
            },
        }
    });
});
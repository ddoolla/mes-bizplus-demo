document.addEventListener('DOMContentLoaded', function () {

    $('#edit-user-form').validate({
        rules: {
            name: 'required',
            password: {
                minlength: 4,
            },
            passwordConfirm: {
                equalTo: '#password',
            },
            email: {
                email: true,
            },
            phone: {
                digits: true,
                rangelength: [11, 11],
            },
        }, messages: {
            name: '사용자 이름을 입력해 주세요.',
            password: {
                minlength: '4자리 이상 입력해 주세요.',
            },
            passwordConfirm: {
                equalTo: '비밀번호가 일치하지 않습니다.',
            },
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
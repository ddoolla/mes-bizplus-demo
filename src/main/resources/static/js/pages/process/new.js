document.addEventListener('DOMContentLoaded', function () {

    $('#process-create-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/processes/check-code',
                    type: 'get',
                },
            },
            name: 'required',
        }, messages: {
            code: {
                required: '공정 코드를 입력해 주세요.',
                remote: '이미 존재하는 공정 코드입니다.',
            },
            name: '공정명을 입력해 주세요.',
        }
    });
});
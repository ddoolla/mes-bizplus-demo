document.addEventListener('DOMContentLoaded', function () {

    $('#worker-edit-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/workers/check-code',
                    type: 'get',
                    data: {
                        id: function () {
                            return $('[name="id"]').val();
                        },
                    },
                },
            },
        }, messages: {
            code: {
                required: '작업자 코드를 입력해 주세요.',
                remote: '이미 존재하는 작업자 코드입니다.',
            },
        }
    });
});
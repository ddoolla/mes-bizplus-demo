document.addEventListener('DOMContentLoaded', function () {

    $('#inspection-spec-edit-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/inspection-specs/check-code',
                    type: 'get',
                    data: {
                        id: function () {
                            return $('[name="id"]').val();
                        },
                    },
                },
            },
            name: 'required',
        }, messages: {
            code: {
                required: '검사기준 코드를 입력해 주세요.',
                remote: '이미 존재하는 검사기준 코드입니다.',
            },
            name: '검사기준명을 입력해 주세요.',
        }
    });
});
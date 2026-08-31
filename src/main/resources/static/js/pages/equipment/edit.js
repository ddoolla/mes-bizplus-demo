document.addEventListener('DOMContentLoaded', function () {

    $('#equipment-edit-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/equipments/check-code',
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
                required: '설비 코드를 입력해 주세요.',
                remote: '이미 존재하는 설비 코드입니다.',
            },
            name: '설비명을 입력해 주세요.',
        }
    });
});
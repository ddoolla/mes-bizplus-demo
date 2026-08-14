document.addEventListener('DOMContentLoaded', function () {

    $('#item-create-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/items/check-code',
                    type: 'get',
                },
            },
            name: 'required',
            type: 'required',
            uomId: 'required',
        }, messages: {
            code: {
                required: '품목 코드를 입력해 주세요.',
                remote: '이미 존재하는 품목 코드입니다.',
            },
            name: '품목명을 입력해 주세요.',
            type: '품목 유형을 선택해 주세요.',
            uomId: '단위를 선택해 주세요.',
        }
    });
});
document.addEventListener('DOMContentLoaded', function () {

    $('#bom-edit-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/boms/check-code',
                    type: 'get',
                    data: {
                        id: function () {
                            return $('[name="id"]').val();
                        }
                    }
                },
            },
            name: 'required',
            itemName: 'required',
        }, messages: {
            code: {
                required: 'BOM 코드를 입력해 주세요.',
                remote: '이미 존재하는 BOM 코드입니다.',
            },
            name: 'BOM명을 입력해 주세요.',
            itemName: '품목을 선택해 주세요.'
        },
    });
});
document.addEventListener('DOMContentLoaded', function () {

    $('#routing-edit-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/routings/check-code',
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
                required: '제품 공정 코드를 입력해 주세요.',
                remote: '이미 존재하는 제품 공정 코드입니다.',
            },
            name: '제품 공정명을 입력해 주세요.',
            itemName: '제품을 선택해 주세요.'
        }
    });
});
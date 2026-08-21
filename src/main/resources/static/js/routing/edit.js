document.addEventListener('DOMContentLoaded', function () {

    const routingEditForm = document.querySelector('#routing-edit-form');

    $(routingEditForm).validate({
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

    $(routingEditForm).find('.routing-process-step-no').each(function () {
        $(this).rules('add', {
            required: true,
            min: 0,
            messages: {
                required: '공정 단계를 입력해 주세요.',
                number: '공정 단계는 숫자로 입력해 주세요.',
                min: '공정 단계는 0 이상이어야 합니다.'
            }
        });
    });
});
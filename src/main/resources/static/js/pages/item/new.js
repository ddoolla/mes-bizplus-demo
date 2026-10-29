document.addEventListener('DOMContentLoaded', function () {

    const lotManagedRadios = document.querySelectorAll('[name="lotManaged"]');
    const shelfLifeInput = document.querySelector('[name="shelfLife"]');
    const shelfLifeUnitInput = document.querySelector('[name="shelfLifeUnit"]');

    /* 유효기간 활성/비활성 */
    lotManagedRadios.forEach(el => {
        el.addEventListener('click', function (e) {
            if (e.target.value === 'true') {
                shelfLifeInput.disabled = false;
                shelfLifeUnitInput.disabled = false;

            } else {
                shelfLifeInput.disabled = true;
                shelfLifeUnitInput.disabled = true;
            }
        });
    });

    /* 등록 폼 유효성 검사 */
    $('#item-new-form').validate({
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
            unitPrice: {
                number: true,
                min: 0,
            },
        }, messages: {
            code: {
                required: '품목 코드를 입력해 주세요.',
                remote: '이미 존재하는 품목 코드입니다.',
            },
            name: '품목명을 입력해 주세요.',
            type: '품목 유형을 선택해 주세요.',
            uomId: '단위를 선택해 주세요.',
            unitPrice: {
                number: '숫자만 입력해 주세요.',
                min: '0 이상 입력해 주세요.',
            },
        }
    });
});
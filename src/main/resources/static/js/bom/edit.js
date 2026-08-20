document.addEventListener('DOMContentLoaded', function () {

    const bomEditForm = document.querySelector('#bom-edit-form');

    $(bomEditForm).validate({
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

    $.validator.addMethod('quantityScale', function (value, element) {
        if (this.optional(element)) {
            return true;
        }

        const scale = parseInt(
            $(element)
                .closest('tr')
                .find('.bom-item-uom option:selected')
                .data('scale'),
            10
        );

        if (isNaN(scale)) {
            return false;
        }

        const decimalPart = value.split('.')[1];

        return !decimalPart || decimalPart.length <= scale;

    }, function (params, element) {
        const scale = parseInt(
            $(element)
                .closest('tr')
                .find('.bom-item-uom option:selected')
                .data('scale'),
            10
        );

        return `해당 단위는 소수점 ${scale}자리까지 입력할 수 있습니다.`;
    });

    $(bomEditForm).find('.bom-item-quantity').each(function () {
        $(this).rules('add', {
            required: true,
            min: 0,
            quantityScale: true,
            messages: {
                required: '수량을 입력해 주세요.',
                number: '수량은 숫자로 입력해 주세요.',
                min: '수량은 0 이상이어야 합니다.'
            }
        });
    });

    $('.bom-item-uom').on('change', function () {
        $(this)
            .closest('tr')
            .find('.bom-item-quantity')
            .valid();
    });
});
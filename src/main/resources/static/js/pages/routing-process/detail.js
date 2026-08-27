document.addEventListener('DOMContentLoaded', function () {

    const processMaterialEditForm = document.querySelector('#process-material-edit-form');

    $(processMaterialEditForm).validate();

    /* 수량 소수점 자릿수 검사 */
    $.validator.addMethod('quantityScale', function (value, element) {
        if (this.optional(element)) {
            return true;
        }

        const scale = parseInt(
            $(element)
                .closest('tr')
                .find('.process-material-uom option:selected')
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
                .find('.process-material-uom option:selected')
                .data('scale'),
            10
        );

        return `해당 단위는 소수점 ${scale}자리까지 입력할 수 있습니다.`;
    });

    $(processMaterialEditForm).find('.process-material-quantity').each(function () {
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

    $('.process-material-uom').on('change', function () {
        $(this)
            .closest('tr')
            .find('.process-material-quantity')
            .valid();
    });
});
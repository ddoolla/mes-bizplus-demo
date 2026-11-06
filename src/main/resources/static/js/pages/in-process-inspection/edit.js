import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    datepicker.init('[name="date"]');

    /* 폼 유효성 검사 */
    $('#in-process-inspection-edit-form').validate({
        rules: {
            date: 'required',
            inspectedQuantity: {
                required: true,
                number: true,
                positive: true,
                quantityScale: true
            },
        }, messages: {
            date: '검사 일자를 선택해 주세요.',
            inspectedQuantity: {
                required: '검사 수량을 입력해 주세요.',
                number: '숫자만 입력해 주세요.',
                positive: '0보다 큰 값을 입력해 주세요.',
            },
        }, errorPlacement: function (error, element) {
            const errorContainers = {
                inspectedQuantity: '#quantity-error-container',
            };

            const container = errorContainers[element.attr('name')];

            if (container) {
                error.appendTo(container);
            } else {
                error.insertAfter(element);
            }
        },
    });

    $.validator.addMethod('quantityScale', function (value, element) {
        if (this.optional(element)) {
            return true;
        }

        const scale = parseInt($('input[name="uomScale"]').val(), 10);

        if (isNaN(scale)) {
            return false;
        }

        const decimalPart = value.split('.')[1];

        return !decimalPart || decimalPart.length <= scale;

    }, function (params, element) {
        const scale = parseInt($('input[name="uomScale"]').val(), 10);

        return `해당 단위는 소수점 ${scale}자리까지 입력할 수 있습니다.`;
    });
});
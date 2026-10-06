import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    const editForm = document.querySelector('#production-order-edit-form');

    datepicker.init('[name="dueDate"]');

    $(editForm).validate({
        rules: {
            quantity: {
                required: true,
                number: true,
                positive: true,
            },
            dueDate: 'required'
        }, messages: {
            quantity: {
                required: '수량을 입력해 주세요.',
                number: '숫자만 입력해 주세요.',
                positive: '0보다 큰 값을 입력해 주세요.',
            },
            dueDate: '생산 예정일을 선택해 주세요.',
        },
    });

    $.validator.addMethod('quantityScale', function (value, element) {
        const scale = editForm.querySelector('[name="uomScale"]').value;
        const decimalPart = value.split('.')[1];

        return !decimalPart || decimalPart.length <= scale;

    }, function (params, element) {
        const scale = editForm.querySelector('[name="uomScale"]').value;

        return `해당 단위는 소수점 ${scale}자리까지 입력할 수 있습니다.`;
    })

    $(editForm).find('.work-order-quantity').each(function () {
        $(this).rules('add', {
            required: true,
            positive: true,
            quantityScale: true,
            messages: {
                required: '수량을 입력해 주세요.',
                number: '수량은 숫자로 입력해 주세요.',
                positive: '0보다 큰 값을 입력해 주세요.'
            }
        });
    });

    $(editForm).find('.work-order-date').each(function () {
        $(this).rules('add', {
            required: true,
            messages: {
                required: '날짜를 선택해 주세요.',
            }
        });
    });
});
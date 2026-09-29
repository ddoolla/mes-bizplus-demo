import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    datepicker.init('[name="dueDate"]');

    $('#production-order-edit-form').validate({
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
});
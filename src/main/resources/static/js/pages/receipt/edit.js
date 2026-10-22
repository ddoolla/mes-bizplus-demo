import createPartnerSingleListModal from "../../domain/partner/modal/list/single.js";
import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    const editForm = document.querySelector('#receipt-edit-form');
    const partnerNameInput = editForm.querySelector('[name="partnerName"]');
    const partnerIdInput = editForm.querySelector('[name="partnerId"]');
    const partnerListButton = document.querySelector('#partner-list-button');

    const partnerSingleListModal = createPartnerSingleListModal();

    datepicker.init('[name="date"]');

    /* 거래처 선택 모달 */
    partnerListButton.addEventListener('click', function () {
        partnerSingleListModal.open({
            title: '매출처 목록',
            url: '/partners/modal/list/single',
            params: {type: 'SUPPLIER'},
            onSelect: (partner) => {
                partnerIdInput.value = partner.id;
                partnerNameInput.value = partner.name;
                partnerSingleListModal.close();
            }
        });
    });

    /* 폼 유효성 검사 */
    $(editForm).validate({
        rules: {
            partnerName: 'required',
            date: 'required',
        }, messages: {
            partnerName: '거래처를 선택해 주세요.',
            date: '입고일을 선택해 주세요.',
        }, errorPlacement: function (error, element) {
            if (element.attr('name') === 'partnerName') {
                error.appendTo('#partner-error-container');
            } else {
                error.insertAfter(element);
            }
        },
    });

    $.validator.addMethod('quantityScale', function (value, element) {
        if (this.optional(element)) {
            return true;
        }

        const scale = parseInt(
            $(element)
                .closest('tr')
                .find('.receipt-item-uom option:selected')
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
                .find('.receipt-item-uom option:selected')
                .data('scale'),
            10
        );

        return `해당 단위는 소수점 ${scale}자리까지 입력할 수 있습니다.`;
    });

    $(editForm).find('.receipt-item-quantity').each(function () {
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

    $('.receipt-item-uom').on('change', function () {
        $(this)
            .closest('tr')
            .find('.receipt-item-quantity')
            .valid();
    });
});
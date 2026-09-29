import createPartnerSingleListModal from "../../domain/partner/modal/list/single.js";
import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    const salesOrderEditForm = document.querySelector('#sales-order-edit-form');
    const partnerNameInput = salesOrderEditForm.querySelector('[name="partnerName"]');
    const partnerIdInput = salesOrderEditForm.querySelector('[name="partnerId"]');
    const partnerListButton = document.querySelector('#partner-list-button');

    const partnerSingleListModal = createPartnerSingleListModal();

    datepicker.initRange({
        formId: 'sales-order-edit-form',
        from: 'date',
        to: 'dueDate'
    });

    datepicker.setRange({
        from: 'date',
        to: 'dueDate',
        fromDate: new Date()
    });

    /* 거래처 선택 모달 */
    partnerListButton.addEventListener('click', function () {
        partnerSingleListModal.open({
            title: '매출처 목록',
            url: '/partners/modal/list/single',
            params: {type: 'CUSTOMER'}
        });
    });

    partnerSingleListModal.onSelect((partner) => {
        partnerIdInput.value = partner.id;
        partnerNameInput.value = partner.name;

        partnerSingleListModal.close();
    });

    /* 폼 유효성 검사 */
    $(salesOrderEditForm).validate({
        rules: {
            partnerName: 'required',
            date: 'required',
            dueDate: 'required',
        }, messages: {
            partnerName: '거래처를 선택해 주세요.',
            date: '등록일을 선택해 주세요.',
            dueDate: '납기일을 선택해 주세요.',
        }, errorPlacement: function (error, element) {
            if (element.attr('name') === 'partnerName') {
                error.appendTo('#sales-order-error-container');
            } else {
                error.insertAfter(element);
            }
        },
    });

    $(salesOrderEditForm).find('.sales-order-item-quantity').each(function () {
        $(this).rules('add', {
            required: true,
            positive: true,
            messages: {
                required: '수량을 입력해 주세요.',
                number: '수량은 숫자로 입력해 주세요.',
                positive: '0보다 큰 값을 입력해 주세요.'
            }
        });
    });
});
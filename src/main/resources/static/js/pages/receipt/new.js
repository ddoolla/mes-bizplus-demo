import datepicker from "../../common/datepicker.js";
import createPartnerSingleListModal from "../../domain/partner/modal/list/single.js";

document.addEventListener('DOMContentLoaded', function () {

    const partnerNameInput = document.querySelector('[name="partnerName"]');
    const partnerIdInput = document.querySelector('[name="partnerId"]');

    const partnerListButton = document.querySelector('#partner-list-button');

    const partnerSingleListModal = createPartnerSingleListModal();

    datepicker.init('[name="date"]');

    /* 거래처 선택 모달 */
    partnerListButton.addEventListener('click', function () {
        partnerSingleListModal.open({
            title: '매출처 목록',
            url: '/partners/modal/list/single',
            params: {type: 'CUSTOMER'},
            onSelect: (partner) => {
                partnerIdInput.value = partner.id;
                partnerNameInput.value = partner.name;
                partnerSingleListModal.close();
            }
        });
    });

    /* 폼 유효성 검사 */
    $('#receipt-new-form').validate({
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
});
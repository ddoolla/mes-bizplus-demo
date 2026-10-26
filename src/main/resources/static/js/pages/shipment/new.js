import datepicker from "../../common/datepicker.js";
import createPartnerSingleListModal from "../../domain/partner/modal/list/single.js";
import createSalesOrderSelectModal from "../../domain/sales-order/modal/select.js";
import toast from "../../common/toast.js";

document.addEventListener('DOMContentLoaded', function () {

    const partnerIdInput = document.querySelector('[name="partnerId"]');
    const partnerNameInput = document.querySelector('[name="partnerName"]');
    const salesOrderIdInput = document.querySelector('[name="salesOrderId"]');
    const salesOrderNoInput = document.querySelector('[name="salesOrderNo"]');
    const partnerListButton = document.querySelector('#partner-list-button');
    const salesOrderListButton = document.querySelector('#sales-order-list-button');

    const partnerSingleListModal = createPartnerSingleListModal();
    const salesOrderSelectModal = createSalesOrderSelectModal();

    let partnerId = null;
    let partnerName = null;

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

                partnerId = partner.id;
                partnerName = partner.name;

                partnerSingleListModal.close();
            }
        });
    });

    /* 수주 목록 버튼 */
    salesOrderListButton.addEventListener('click', function (e) {
        if (!partnerId) {
            toast.warning({
                message: '먼저 거래처를 선택해 주세요.'
            });

            return;
        }

        salesOrderSelectModal.open({
            title: `수주 선택 - ${partnerName}`,
            url: '/sales-orders/modal/select',
            params: {partnerId},
            onSelect: (salesOrder) => {
                salesOrderIdInput.value = salesOrder.id;
                salesOrderNoInput.value = salesOrder.orderNo;
            }
        });
    });

    /* 폼 유효성 검사 */
    $('#shipment-new-form').validate({
        rules: {
            partnerName: 'required',
            date: 'required',
        }, messages: {
            partnerName: '거래처를 선택해 주세요.',
            date: '출고일을 선택해 주세요.',
        }, errorPlacement: function (error, element) {
            if (element.attr('name') === 'partnerName') {
                error.appendTo('#partner-error-container');
            } else {
                error.insertAfter(element);
            }
        },
    });
});
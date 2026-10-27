import createPartnerSingleListModal from "../../domain/partner/modal/list/single.js";
import datepicker from "../../common/datepicker.js";
import createSalesOrderSelectModal from "../../domain/sales-order/modal/select.js";
import toast from "../../common/toast.js";

document.addEventListener('DOMContentLoaded', function () {

    const editForm = document.querySelector('#shipment-edit-form');
    const partnerIdInput = document.querySelector('[name="partnerId"]');
    const partnerNameInput = document.querySelector('[name="partnerName"]');
    const salesOrderIdInput = document.querySelector('[name="salesOrderId"]');
    const salesOrderNoInput = document.querySelector('[name="salesOrderNo"]');
    const partnerListButton = document.querySelector('#partner-list-button');
    const salesOrderListButton = document.querySelector('#sales-order-list-button');

    const partnerSingleListModal = createPartnerSingleListModal();
    const salesOrderSelectModal = createSalesOrderSelectModal();

    let partnerId = partnerIdInput.value ?? null;
    let partnerName = partnerNameInput.value ?? null;

    datepicker.init('[name="date"]');

    /* 거래처 선택 모달 */
    partnerListButton.addEventListener('click', function () {
        partnerSingleListModal.open({
            title: '매출처 목록',
            url: '/partners/modal/list/single',
            params: {type: 'CUSTOMER'},
            onSelect: (partner) => {
                // 수주 정보 초기화
                salesOrderIdInput.value = '';
                salesOrderNoInput.value = '';

                // 거래처 정보 입력
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
    $(editForm).validate({
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

    $.validator.addMethod('quantityScale', function (value, element) {
        if (this.optional(element)) {
            return true;
        }

        const scale = Number(element.dataset.uomScale);

        if (isNaN(scale)) {
            return false;
        }

        const decimalPart = value.split('.')[1];

        return !decimalPart || decimalPart.length <= scale;

    }, function (params, element) {
        const scale = Number(element.dataset.uomScale);

        return `해당 단위는 소수점 ${scale}자리까지 입력할 수 있습니다.`;
    });

    $(editForm).find('.shipment-item-quantity').each(function () {
        $(this).rules('add', {
            required: true,
            positive: true,
            quantityScale: true,
            max: function (element) {
                return Number(element.dataset.stock);
            },
            messages: {
                required: '수량을 입력해 주세요.',
                number: '수량은 숫자로 입력해 주세요.',
                positive: '0보다 큰 값을 입력해 주세요.',
                max: '재고 수량을 초과할 수 없습니다.'
            }
        });
    });
});
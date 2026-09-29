import createItemSingleListModal from "../../domain/item/modal/list/single.js";
import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    const createForm = document.querySelector('#production-order-new-form');
    const modalOpenButton = document.querySelector('#item-list-button');

    const itemSingleListModal = createItemSingleListModal();

    datepicker.init('[name="dueDate"]');
    datepicker.set('[name="dueDate"]', new Date())

    // 품목 선택 모달 열기
    modalOpenButton.addEventListener('click', function () {
        itemSingleListModal.open({
            title: '제품 목록 (완제품/반제품)',
            url: '/items/modal/list/single',
            params: {group: 'PRODUCT'}
        });
    });

    // 품목 선택 처리
    itemSingleListModal.onSelect(function (item) {
        createForm.querySelector('[name="itemId"]').value = item.id;
        createForm.querySelector('[name="itemName"]').value = item.name;
        itemSingleListModal.close();
    });


    $('#production-order-new-form').validate({
        rules: {
            itemName: 'required',
            quantity: {
                required: true,
                number: true,
                positive: true,
            },
            dueDate: 'required'
        }, messages: {
            itemName: '제품을 선택해 주세요.',
            quantity: {
                required: '수량을 입력해 주세요.',
                number: '숫자만 입력해 주세요.',
                positive: '0보다 큰 값을 입력해 주세요.',
            },
            dueDate: '생산 예정일을 선택해 주세요.',
        }, errorPlacement: function (error, element) {
            if (element.attr('name') === 'itemName') {
                error.appendTo('#item-error-container');
            } else {
                error.insertAfter(element);
            }
        },
    });
});
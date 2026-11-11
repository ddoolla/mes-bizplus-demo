import datepicker from "../../common/datepicker.js";
import createItemSelectModal from "../../domain/item/modal/select.js";
import createLotSelectModal from "../../domain/lot/modal/select.js";
import toast from "../../common/toast.js";

document.addEventListener('DOMContentLoaded', function () {

    const itemIdInput = document.querySelector('[name="itemId"]');
    const itemNameInput = document.querySelector('[name="itemName"]');

    const lotIdInput = document.querySelector('[name="lotId"]');
    const lotNoInput = document.querySelector('[name="lotNo"]');
    const beforeQuantityDisplayInput = document.querySelector('[name="beforeQuantityDisplay"]');
    const beforeQuantityInput = document.querySelector('[name="beforeQuantity"]');

    const itemListButton = document.querySelector('#item-list-button');
    const lotListButton = document.querySelector('#lot-list-button');

    const itemSelectModal = createItemSelectModal();
    const lotSelectModal = createLotSelectModal();

    let itemId = null;
    let lotManaged = null;

    datepicker.init('[name="date"]');

    /* 품목 선택 모달 */
    itemListButton.addEventListener('click', function () {
        itemSelectModal.open({
            title: '품목 목록',
            url: '/items/modal/select/list',
            onSelect: (item) => {
                // 데이터 초기화
                lotIdInput.value = '';
                lotNoInput.value = '';
                beforeQuantityDisplayInput.value = '';
                beforeQuantityInput.value = '';

                // 데이터 입력
                itemIdInput.value = item.id;
                itemNameInput.value = item.name;

                itemId = item.id;
                lotManaged = item.lotManaged;

                itemSelectModal.close();
            }
        });
    });

    /* LOT 선택 모달 */
    lotListButton.addEventListener('click', function (e) {
        if (!itemId) {
            toast.warning({
                message: '먼저 품목을 선택해 주세요.',
            });

            return;
        }

        if (lotManaged === 'false') {
            toast.warning({
                message: 'LOT 관리 품목이 아닙니다.',
            });

            return;
        }

        lotSelectModal.open({
            title: 'LOT 목록',
            url: '/lots/modal/select/list',
            params: {itemId},
            onSelect: (lot) => {
                lotIdInput.value = lot.id;
                lotNoInput.value = lot.no;
                beforeQuantityDisplayInput.value = lot.quantityDisplay;
                beforeQuantityInput.value = lot.quantity;

                lotSelectModal.close();
            }
        });
    });

    /* 폼 유효성 검사 */
    $('#new-form').validate({
        rules: {
            itemName: 'required',
            date: 'required',
            afterQuantity: {
                required: true,
                number: true,
                min: 0,
            },
        }, messages: {
            itemName: '품목을 선택해 주세요.',
            date: '조정일을 선택해 주세요.',
            afterQuantity: {
                required: '조정 후 수량을 입력해 주세요.',
                number: '숫자만 입력해 주세요.',
                min: '0 이상 입력해 주세요.',
            },
        }, errorPlacement: function (error, element) {
            if (element.attr('name') === 'itemName') {
                error.appendTo('#partner-error-container');
            } else {
                error.insertAfter(element);
            }
        },
    });
});
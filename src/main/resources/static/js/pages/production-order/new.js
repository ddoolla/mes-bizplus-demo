import createItemSingleListModal from "../../domain/item/modal/list/single.js";
import datepicker from "../../common/datepicker.js";
import createBomSingleListModal from "../../domain/bom/modal/list/single.js";
import createRoutingSingleListModal from "../../domain/routing/modal/list/single.js";

document.addEventListener('DOMContentLoaded', function () {

    const createForm = document.querySelector('#production-order-new-form');
    const itemListButton = document.querySelector('#item-list-button');
    const bomListButton = document.querySelector('#bom-list-button');
    const routingListButton = document.querySelector('#routing-list-button');

    const itemIdInput = createForm.querySelector('[name="itemId"]');
    const itemNameInput = createForm.querySelector('[name="itemName"]');
    const bomIdInput = createForm.querySelector('[name="bomId"]');
    const bomNameInput = createForm.querySelector('[name="bomName"]');
    const routingIdInput = createForm.querySelector('[name="routingId"]');
    const routingNameInput = createForm.querySelector('[name="routingName"]');

    const itemSingleListModal = createItemSingleListModal();
    const bomSingleListModal = createBomSingleListModal();
    const routingSingleListModal = createRoutingSingleListModal();

    datepicker.init('[name="dueDate"]');
    datepicker.set('[name="dueDate"]', new Date())

    /* 품목 선택 모달 열기 */
    itemListButton.addEventListener('click', function () {
        itemSingleListModal.open({
            title: '제품 목록 (완제품/반제품)',
            url: '/items/modal/list/single',
            params: {group: 'PRODUCT'},
            onSelect: (item) => {
                // BOM, 제품공정 정보 초기화
                bomIdInput.value = '';
                bomNameInput.value = '';
                routingIdInput.value = '';
                routingNameInput.value = '';

                // 제품 정보 입력
                itemIdInput.value = item.id;
                itemNameInput.value = item.name;

                validator.element(itemNameInput);

                // BOM 정보 입력
                if (item.defaultBom.name) {
                    bomIdInput.value = item.defaultBom.id;
                    bomNameInput.value = item.defaultBom.name;

                    validator.element(bomNameInput);

                } else {
                    bomNameInput.placeholder = '제품 BOM을 선택해 주세요.';
                }

                // 제품공정 정보 입력
                if (item.defaultRouting.name) {
                    routingIdInput.value = item.defaultRouting.id;
                    routingNameInput.value = item.defaultRouting.name;

                    validator.element(routingNameInput);

                } else {
                    routingNameInput.placeholder = '제품 공정을 선택해 주세요.';
                }

                bomListButton.disabled = false;
                routingListButton.disabled = false;

                itemSingleListModal.close();
            }
        });
    });

    /* BOM 선택 */
    bomListButton.addEventListener('click', function (e) {
        const itemId = itemIdInput.value;

        bomSingleListModal.open({
            title: 'BOM 목록',
            url: `/boms/modal/list/single`,
            params: {itemId: itemId},
            onSelect: (bom) => {
                bomIdInput.value = bom.id;
                bomNameInput.value = bom.name;

                bomSingleListModal.close();
            }
        });
    });

    /* 제품공정 선택 */
    routingListButton.addEventListener('click', function (e) {
        const itemId = itemIdInput.value;

        routingSingleListModal.open({
            title: '제품공정 목록',
            url: `/routings/modal/list/single`,
            params: {itemId: itemId}
        });
    });

    routingSingleListModal.onSelect((routing) => {
        routingIdInput.value = routing.id;
        routingNameInput.value = routing.name;

        routingSingleListModal.close();
    });

    /* 등록 유효성 검사 */
    const validator = $('#production-order-new-form').validate({
        rules: {
            itemName: 'required',
            bomName: 'required',
            routingName: 'required',
            quantity: {
                required: true,
                number: true,
                positive: true,
            },
            dueDate: 'required'
        }, messages: {
            itemName: '제품을 선택해 주세요.',
            bomName: '제품 BOM을 선택해 주세요.',
            routingName: '제품 공정을 선택해 주세요.',
            quantity: {
                required: '수량을 입력해 주세요.',
                number: '숫자만 입력해 주세요.',
                positive: '0보다 큰 값을 입력해 주세요.',
            },
            dueDate: '생산 예정일을 선택해 주세요.',
        }, errorPlacement: function (error, element) {
            const errorContainers = {
                itemName: '#item-error-container',
                bomName: '#bom-error-container',
                routingName: '#routing-error-container',
            };

            const container = errorContainers[element.attr('name')];

            if (container) {
                error.appendTo(container);
            } else {
                error.insertAfter(element);
            }
        },
    });
});
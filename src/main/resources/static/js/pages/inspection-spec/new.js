import createItemSingleListModal from "../../domain/item/modal/list/single.js";
import createProcessSingleListModal from "../../domain/process/modal/list/single.js";

document.addEventListener('DOMContentLoaded', function () {

    const typeInput = document.querySelector('[name="type"]');
    const itemIdInput = document.querySelector('[name="itemId"]');
    const itemNameInput = document.querySelector('[name="itemName"]');
    const processIdInput = document.querySelector('[name="processId"]');
    const processNameInput = document.querySelector('[name="processName"]');
    const itemListButton = document.querySelector('#item-list-button');
    const processListButton = document.querySelector('#process-list-button');

    const itemSingleListModal = createItemSingleListModal();
    const processSingleListModal = createProcessSingleListModal();

    /* 검사 유형 선택 */
    typeInput.addEventListener('change', function (e) {
        const isProcess = e.currentTarget.value === 'PROCESS';

        processListButton.disabled = !isProcess;

        if (!isProcess) {
            processIdInput.value = '';
            processNameInput.value = '';
        }
    });

    /* 제품 선택 */
    itemListButton.addEventListener('click', function () {
        itemSingleListModal.open({
            title: '제품 목록',
            url: '/items/modal/list/single',
        });
    });

    itemSingleListModal.onSelect((item) => {
        itemIdInput.value = item.id;
        itemNameInput.value = item.name;
        itemSingleListModal.close();
    });

    /* 공정 선택 */
    processListButton.addEventListener('click', function () {
        processSingleListModal.open();
    });

    processSingleListModal.onSelect((process) => {
        processIdInput.value = process.id;
        processNameInput.value = process.name;
        processSingleListModal.close();
    });

    /* 폼 유효성 검사 */
    $('#inspection-spec-create-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/inspection-specs/check-code',
                    type: 'get',
                },
            },
            name: 'required',
            type: 'required',
            itemName: 'required',
            processName: {
                required: function () {
                    return $('[name="type"]').val() === 'PROCESS';
                },
            },
        }, messages: {
            code: {
                required: '검사기준 코드를 입력해 주세요.',
                remote: '이미 존재하는 검사기준 코드입니다.',
            },
            name: '검사기준명을 입력해 주세요.',
            type: '검사 유형을 선택해 주세요.',
            itemName: '품목을 선택해 주세요.',
            processName: '공정을 선택해 주세요.'
        }, errorPlacement: function (error, element) {
            const errorContainers = {
                itemName: '#item-error-container',
                processName: '#process-error-container',
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
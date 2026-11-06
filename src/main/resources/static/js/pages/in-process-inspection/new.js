import datepicker from "../../common/datepicker.js";
import createInspectionSpecSelectModal from "../../domain/inspection-spec/modal/select.js";
import toast from "../../common/toast.js";
import ajax from "../../common/ajax.js";
import createWorkResultSelectModal from "../../domain/work-result/modal/select.js";

document.addEventListener('DOMContentLoaded', function () {

    const workOrderNoInput = document.querySelector('[name="workOrderNo"]');
    const workResultIdInput = document.querySelector('[name="workResultId"]');
    const processNameInput = document.querySelector('[name="processName"]');
    const itemCodeInput = document.querySelector('[name="itemCode"]');
    const itemNameInput = document.querySelector('[name="itemName"]');
    const lotNoInput = document.querySelector('[name="lotNo"]');
    const workQuantityInput = document.querySelector('[name="workQuantity"]');

    const inspectionSpecNameInput = document.querySelector('[name="inspectionSpecName"]');
    const inspectionSpecIdInput = document.querySelector('[name="inspectionSpecId"]');
    const quantityInput = document.querySelector('[name="quantity"]');
    const uomSelect = document.querySelector('[name="uomId"]');

    const workResultListButton = document.querySelector('#work-result-list-button');
    const inspectionSpecListButton = document.querySelector('#inspection-spec-list-button');

    const workResultSelectModal = createWorkResultSelectModal();
    const inspectionSpecSelectModal = createInspectionSpecSelectModal();

    let itemId = null;
    let processId = null;

    /* 작업실적 선택 */
    workResultListButton.addEventListener('click', function (e) {
        workResultSelectModal.open({
            title: '작업 실적 선택',
            url: '/work-results/modal/select',
            onSelect: async (workResult) => {
                // 검사기준 정보 초기화
                inspectionSpecIdInput.value = '';
                inspectionSpecNameInput.value = '';

                // 작업 실적 정보 입력
                workOrderNoInput.value = workResult.workOrderNo;
                workResultIdInput.value = workResult.id;
                processNameInput.value = workResult.process.name;
                itemCodeInput.value = workResult.item.code;
                itemNameInput.value = workResult.item.name;
                lotNoInput.value = workResult.lotNo;
                workQuantityInput.value = Number(workResult.goodQuantity).toFixed() + ' ' + workResult.item.uom.code;

                // 기본 검사기준 정보 입력
                if (workResult.process.defaultIqcSpecId) {
                    inspectionSpecIdInput.value = workResult.process.defaultIqcSpecId;
                    inspectionSpecNameInput.value = workResult.process.defaultIqcSpecName;
                }

                // 기본 수량 입력
                quantityInput.value = Number(workResult.goodQuantity).toFixed();

                // 단위 선택 옵션 초기화
                try {
                    const response = await ajax.get("/api/convertible-uoms", {toUomId: workResult.item.uom.id});
                    const uoms = response.data;

                    uomSelect.replaceChildren();

                    uoms.forEach(uom => {
                        const option = new Option(uom.code, uom.id);

                        option.dataset.scale = uom.scale;

                        uomSelect.add(option);
                    });

                    uomSelect.disabled = false;
                    uomSelect.value = String(workResult.item.uom.id);

                } catch (xhr) {
                    toast.error({
                        message: '단위 목록을 불러오지 못했습니다.'
                    });
                }

                processId = workResult.process.id;
                itemId = workResult.item.id;

                workResultSelectModal.close();
            }
        });
    });

    /* 검사기준 선택 */
    inspectionSpecListButton.addEventListener('click', function (e) {
        if (!itemId) {
            toast.info({
                message: '먼저 작업 실적을 선택해 주세요.'
            });
            return;
        }

        inspectionSpecSelectModal.open({
            title: '공정 검사기준 선택',
            url: '/inspection-specs/modal/select',
            params: {
                itemId: itemId,
                type: 'IN_PROCESS',
                processId: processId,
            },
            onSelect: (inspectionSpec) => {
                inspectionSpecIdInput.value = inspectionSpec.id;
                inspectionSpecNameInput.value = inspectionSpec.name;

                inspectionSpecSelectModal.close();
            }
        });
    });

    datepicker.init('[name="date"]');

    /* 폼 유효성 검사 */
    $('#incoming-inspection-new-form').validate({
        rules: {
            itemName: 'required',
            inspectionSpecName: 'required',
            date: 'required',
            quantity: {
                required: true,
                number: true,
                positive: true,
                quantityScale: true,
            },
            uomId: 'required',
        }, messages: {
            itemName: '입고 자재를 선택해 주세요.',
            inspectionSpecName: '검사 기준을 선택해 주세요.',
            date: '검사 일자를 선택해 주세요.',
            quantity: {
                required: '검사 수량을 입력해 주세요.',
                number: '숫자만 입력해 주세요.',
                positive: '0 보다 큰 값을 입력해 주세요.',
            },
            uomId: '검사 단위를 선택해 주세요.',
        }, errorPlacement: function (error, element) {
            const errorContainers = {
                itemName: 'receipt-item-error-container',
                inspectionSpecName: 'inspection-spec-error-container',
            };

            const container = errorContainers[element.attr('name')];

            if (container) {
                error.appendTo(container);
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
                .closest('form')
                .find('select[name="uomId"] option:selected')
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
                .closest('form')
                .find('select[name="uomId"] option:selected')
                .data('scale'),
            10
        );

        return `해당 단위는 소수점 ${scale}자리까지 입력할 수 있습니다.`;
    });
});
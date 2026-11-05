import ajax from "../../common/ajax.js";
import createConfirmModal from "../../common/modal/confirm.js";
import toast from "../../common/toast.js";
import createEquipmentSingleListModal from "../../domain/equipment/modal/list/single.js";
import createMaterialInputModal from "../../domain/material-input/modal/input.js";
import createWorkResultInputModal from "../../domain/work-result/modal/input.js";

document.addEventListener('DOMContentLoaded', function () {

    const startBtn = document.querySelector('#work-start-button');
    const stopBtn = document.querySelector('#work-stop-button');
    const resumeBtn = document.querySelector('#work-resume-button');
    const completeBtn = document.querySelector('#work-complete-button');
    const inputBtn = document.querySelector('#material-input-button');
    const progressbar = document.querySelector('.progress-bar');
    const progressRateSpan = document.querySelector('#progress-rate');
    const equipmentListButton = document.querySelector('#equipment-list-button');

    const productionCountSpan = document.querySelector('#production-count');
    const goodCountSpan = document.querySelector('#good-count');
    const defectCountSpan = document.querySelector('#defect-count');

    const workOrderId = document.querySelector('[name="id"]').value;
    const routingProcessId = document.querySelector('[name="routingProcessId"]').value;
    const equipmentId = document.querySelector('[name="equipmentId"]').value;
    const workStatus = document.querySelector('[name="status"]').value;
    const targetCount = document.querySelector('[name="quantity"]').value;
    const productionQuantity = document.querySelector('[name="productionQuantity"]').value;
    const goodQuantity = document.querySelector('[name="goodQuantity"]').value;
    const defectQuantity = document.querySelector('[name="defectQuantity"]').value;

    const confirmModal = createConfirmModal();
    const equipmentSingleListModal = createEquipmentSingleListModal();
    const workResultInputModal = createWorkResultInputModal();
    const materialInputModal = createMaterialInputModal();

    /* 설비 변경 */
    if (equipmentListButton) {
        equipmentListButton.addEventListener('click', function () {
            equipmentSingleListModal.open({
                title: '설비 변경',
                url: `/equipments/modal/list/single`,
                onSelect: async (equipment) => {
                    try {
                        const response = await ajax.patch(
                            `/work-orders/${workOrderId}/equipment`, equipment.id
                        );

                        toast.afterReload({
                            message: response.message,
                        });

                        equipmentSingleListModal.close();

                        location.reload();

                    } catch (xhr) {
                        toast.error({
                            message: xhr.responseJSON?.message,
                        });
                    }
                }
            })
        });
    }

    // PLC 생산 데이터 조회 함수
    const fetchSensorData = async () => {
        if (workStatus !== 'IN_PROGRESS') {
            return;
        }

        if (!equipmentId) {
            return;
        }

        try {
            const response = await ajax.get(
                `/api/sensor-events/equipments/${equipmentId}/current`,
                {sensorCode: '0003'} // 생산 카운트
            );

            // PLC 생산 카운트 (작업 시작, 재개 시 초기화)
            const plcCount = response.data.numericValue ?? 0;

            const productionCount = Number(productionQuantity) + Number(plcCount);
            const goodCount = Number(goodQuantity) + Number(plcCount);

            // 생산 현황 업데이트 (생산 카운트, 양품 수량)
            productionCountSpan.textContent = productionCount.toLocaleString();
            goodCountSpan.textContent = goodCount.toLocaleString();

            // 진행률 업데이트
            if (Number(targetCount) !== 0) {
                const progressRate = ((productionCount / targetCount) * 100).toFixed() + '%';
                progressbar.style.width = progressRate;
                progressRateSpan.textContent = progressRate;
            }

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON?.message ?? '설비 연결을 확인해 주세요.'
            })
        }

        if (workStatus === 'IN_PROGRESS') {
            setTimeout(fetchSensorData, 5000);
        }
    };

    /* 생산 데이터 조회 */
    window.addEventListener('load', function () {
        if (workStatus === 'IN_PROGRESS') {
            fetchSensorData();
        }
    });

    /* 작업 시작 */
    if (startBtn) {
        startBtn.addEventListener('click', function (e) {
            if (workStatus !== 'PENDING') {
                return;
            }

            confirmModal.open({
                title: '작업 시작 확인',
                content: '작업을 시작하시겠습니까?',
                onConfirm: async () => {
                    try {
                        const response = await ajax.patch(`/work-orders/${workOrderId}/start`)

                        toast.afterReload({
                            message: response.message,
                        });

                        location.reload();

                    } catch (xhr) {
                        toast.error({
                            message: xhr.responseJSON?.message,
                        });
                    }
                }
            });
        });
    }

    /* 작업 중지 */
    if (stopBtn) {
        stopBtn.addEventListener('click', function () {
            if (workStatus !== 'IN_PROGRESS') {
                toast.warning({
                    message: '현재 작업이 진행 중인 상태가 아닙니다.',
                });

                return;
            }

            workResultInputModal.open({
                title: '작업 중지 - 작업 실적 저장',
                prodQty: Number(productionCountSpan.textContent.replaceAll(/[,.]/g, '')),
                goodQty: Number(goodCountSpan.textContent.replaceAll(/[,.]/g, '')),
                defectQty: Number(defectCountSpan.textContent.replaceAll(/[,.]/g, '')),
                onSubmit: async (form) => {
                    try {
                        const response = await ajax.patch(
                            `/work-orders/${workOrderId}/stop`,
                            new FormData(form)
                        );

                        toast.afterReload({
                            message: response.message,
                        });

                        workResultInputModal.close();

                        location.reload();

                    } catch (xhr) {
                        toast.error({
                            message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
                        });
                    }
                }
            });
        });
    }

    if (resumeBtn) {
        /* 작업 재개 */
        resumeBtn.addEventListener('click', function () {
            if (workStatus !== 'PAUSED') {
                toast.warning({
                    message: '작업 중지 상태인 경우에만 재개가 가능합니다.',
                });

                return;
            }

            confirmModal.open({
                title: '작업 재개 확인',
                content: '작업을 재개하시겠습니까?',
                onConfirm: async () => {
                    try {
                        const response = await ajax.patch(`/work-orders/${workOrderId}/resume`)

                        toast.afterReload({
                            message: response.message,
                        });

                        location.reload();

                    } catch (xhr) {
                        toast.error({
                            message: xhr.responseJSON?.message,
                        });
                    }
                }
            });
        });
    }

    /* 작업 완료 */
    completeBtn.addEventListener('click', function () {
        if (workStatus !== 'IN_PROGRESS' && workStatus !== 'PAUSED') {
            toast.warning({
                message: '작업 진행중 또는 정지 상태에서만 완료할 수 있습니다.',
            });

            return;
        }

        workResultInputModal.open({
            title: '작업 완료 - 작업 실적 저장',
            prodQty: Number(productionCountSpan.textContent.replaceAll(/[,.]/g, '')),
            goodQty: Number(goodCountSpan.textContent.replaceAll(/[,.]/g, '')),
            defectQty: Number(defectCountSpan.textContent.replaceAll(/[,.]/g, '')),
            onSubmit: async (form) => {
                try {
                    const response = await ajax.patch(
                        `/work-orders/${workOrderId}/complete`,
                        new FormData(form)
                    );

                    toast.afterReload({
                        message: response.message,
                    });

                    workResultInputModal.close();

                    location.reload();

                } catch (xhr) {
                    toast.error({
                        message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
                    });
                }
            }
        });
    });

    /* 자재 투입 */
    inputBtn.addEventListener('click', function () {
        if (workStatus !== 'IN_PROGRESS') {
            toast.warning({
                message: '현재 작업이 진행 중인 상태가 아닙니다.',
            });

            return;
        }

        materialInputModal.open();
    });
});
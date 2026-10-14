import ajax from "../../common/ajax.js";
import createConfirmModal from "../../common/modal/confirm.js";
import toast from "../../common/toast.js";

document.addEventListener('DOMContentLoaded', function () {

    const startBtn = document.querySelector('#work-start-button');
    const stopBtn = document.querySelector('#work-stop-button');
    const resumeBtn = document.querySelector('#work-resume-button');
    const completeBtn = document.querySelector('#work-complete-button');
    const progressbar = document.querySelector('.progress-bar');
    const progressRateSpan = document.querySelector('#progress-rate');

    const productionCount = document.querySelector('#production-count');
    const goodCount = document.querySelector('#good-count');
    const defectCount = document.querySelector('#defect-count');

    const workOrderId = document.querySelector('[name="id"]').value;
    const equipmentId = document.querySelector('[name="equipmentId"]').value;
    const workStatus = document.querySelector('[name="status"]').value;
    const targetCount = document.querySelector('[name="quantity"]').value;

    const confirmModal = createConfirmModal();

    // PLC 생산 데이터 조회 함수
    const fetchSensorData = async () => {
        if (workStatus !== 'IN_PROGRESS') {
            return;
        }

        try {
            const response = await ajax.get(
                `/api/sensor-events/equipments/${equipmentId}/current`,
                {sensorCode: '0003'}
            );

            // PLC 생산 카운트
            const plcCount = response.data.numericValue ?? 0;

            // 생산 현황 업데이트
            productionCount.textContent = plcCount;
            goodCount.textContent = plcCount;

            // todo 불량 카운트
            // todo 진행률도 ㅇ

            // 진행률 업데이트
            if (targetCount !== 0) {
                const progressRate = ((plcCount / targetCount) * 100).toFixed() + '%';
                progressbar.style.width = progressRate;
                progressRateSpan.textContent = progressRate;
            }

        } catch (xhr) {
            alert(xhr.responseJSON?.message);
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

            confirmModal.open({
                title: '작업 중지 확인',
                content: '작업을 중지하시겠습니까?',
                onConfirm: async () => {
                    try {
                        const response = await ajax.patch(`/work-orders/${workOrderId}/stop`)

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
        if (workStatus !== 'IN_PROGRESS') {
            toast.warning({
                message: '현재 작업이 진행 중인 상태가 아닙니다.',
            });

            return;
        }

        confirmModal.open({
            title: '작업 완료 확인',
            content: '작업을 완료하시겠습니까?',
            onConfirm: async () => {
                try {
                    const response = await ajax.patch(`/work-orders/${workOrderId}/complete`)

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
});
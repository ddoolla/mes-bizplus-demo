import datepicker from "../../../common/datepicker.js";
import createEquipmentSingleListModal from "../../../domain/equipment/modal/list/single.js";
import createWorkerSingleListModal from "../../../domain/worker/modal/list/single.js";
import checkbox from "../../../common/checkbox.js";
import toast from "../../../common/toast.js";
import ajax from "../../../common/ajax.js";
import createConfirmModal from "../../../common/modal/confirm.js";
import createWorkOrderAddModal from "../../../domain/work-order/modal/add.js";

document.addEventListener('DOMContentLoaded', function () {


    const checkboxGroup = document.querySelector('#work-order-table');
    const createButton = document.querySelector('#work-order-create-button');
    const deleteButton = document.querySelector('#work-order-delete-button');
    const equipmentListButtons = document.querySelectorAll('.equipment-list-button');
    const workerListButtons = document.querySelectorAll('.worker-list-button');

    const confirmModal = createConfirmModal();
    const equipmentSingleListModal = createEquipmentSingleListModal();
    const workerSingleListModal = createWorkerSingleListModal();
    const workOrderAddModal = createWorkOrderAddModal()

    checkbox.init(checkboxGroup);
    datepicker.init('.work-order-date');

    /* 설비 선택 모달 */
    equipmentListButtons.forEach(button => {
        button.addEventListener('click', function (e) {
            const {index} = e.currentTarget.dataset;

            const equipmentIdInput = document.querySelector(`[name="workOrders[${index}].equipmentId"]`);
            const equipmentNameInput = document.querySelector(`[name="workOrders[${index}].equipmentName"]`);

            equipmentSingleListModal.open({
                title: '설비 선택',
                url: '/equipments/modal/list/single',
                onSelect: (equipment) => {
                    equipmentIdInput.value = equipment.id;
                    equipmentNameInput.value = equipment.name;

                    equipmentSingleListModal.close();
                }
            })
        });
    });

    /* 작업자 선택 모달 */
    workerListButtons.forEach(button => {
        button.addEventListener('click', function (e) {
            const {index} = e.currentTarget.dataset;

            const workerIdInput = document.querySelector(`[name="workOrders[${index}].workerId"]`);
            const workerNameInput = document.querySelector(`[name="workOrders[${index}].workerName"]`);

            workerSingleListModal.open({
                title: '작업자 선택',
                url: '/workers/modal/list/single',
                onSelect: (worker) => {
                    workerIdInput.value = worker.id;
                    workerNameInput.value = worker.name;

                    workerSingleListModal.close();
                }
            })
        });
    });

    /* 작업지시 추가 */
    createButton.addEventListener('click', function (e) {
        workOrderAddModal.open({
            title: '작업지시 추가',
            onRegister: async (selectedIds) => {
                try {
                    const response = await ajax.post(`/work-orders`, {productionOrderProcessIds: selectedIds});

                    toast.afterReload({
                        message: response.message
                    });

                    workOrderAddModal.close();

                    location.reload();

                } catch (xhr) {
                    toast.error({
                        message: xhr.responseJSON?.message
                    });
                }
            }
        });
    });

    /* 작업지시 삭제 */
    deleteButton.addEventListener('click', async function () {
        const selectedIds = checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {
            toast.error({
                message: '항목을 선택해 주세요.',
            });

            return;
        }

        confirmModal.open({
            title: '삭제 확인',
            content: '정말 삭제하시겠습니까?',
            onConfirm: async () => {
                try {
                    const response = await ajax.delete('/work-orders', selectedIds);

                    toast.afterReload({
                        message: response.message,
                    });

                    location.reload();

                } catch (xhr) {
                    toast.error({
                        message: xhr.responseJSON.message,
                    });
                }
            }
        });
    })
});
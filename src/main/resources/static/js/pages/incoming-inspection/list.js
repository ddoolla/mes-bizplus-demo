import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createConfirmModal from "../../common/modal/confirm.js";
import toast from "../../common/toast.js";
import datepicker from "../../common/datepicker.js";
import createIncomingInspectionCreateModal from "../../domain/receipt-item/modal/create.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#incoming-inspection-table');
    const createButton = document.querySelector('#incoming-inspection-create-button');
    const deleteButton = document.querySelector('#incoming-inspection-delete-button');

    const confirmModal = createConfirmModal();
    const incomingInspectionCreateModal = createIncomingInspectionCreateModal();

    datepicker.initRange({
        formId: 'incoming-inspection-search-form',
        from: 'startDate',
        to: 'endDate'
    });

    checkbox.init(checkboxGroup);

    /* 입고검사 등록 */
    createButton.addEventListener('click', function () {
        incomingInspectionCreateModal.open({
            title: '입고 자재 선택',
            url: '/incoming-inspections/modal/create',
            onSelect: (receiptItem) => {

            }
        });
    });

    /* 입고검사 삭제 */
    deleteButton.addEventListener('click', async function () {
        const selectedIds = checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {
            toast.error({
                message: '항목을 선택해 주세요.'
            });

            return;
        }

        confirmModal.open({
            title: '삭제 확인',
            content: '정말 삭제하시겠습니까?',
            onConfirm: async () => {
                try {
                    const response = await ajax.delete('/api/incoming-inspections', selectedIds);

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
    });
});
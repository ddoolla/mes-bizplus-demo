import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createConfirmModal from "../../common/modal/confirm.js";
import toast from "../../common/toast.js";
import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#receipt-table');
    const deleteButton = document.querySelector('#receipt-delete-button');
    const excelButton = document.querySelector('#excel-download-button');

    const confirmModal = createConfirmModal();

    datepicker.initRange({
        formId: 'receipt-search-form',
        from: 'startDate',
        to: 'endDate'
    });

    checkbox.init(checkboxGroup);

    // 엑셀 다운로드
    excelButton.addEventListener('click', function () {
        const form = document.querySelector('#receipt-search-form');
        const params = new URLSearchParams(new FormData(form));

        window.location.href = `/api/receipts/excel?${params.toString()}`;
    });

    // 삭제
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
                    const response = await ajax.delete('/api/receipts', selectedIds);

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
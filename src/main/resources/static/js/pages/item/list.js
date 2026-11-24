import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import toast from "../../common/toast.js";
import createConfirmModal from "../../common/modal/confirm.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#item-table');
    const deleteButton = document.querySelector('#item-delete-button');
    const searchForm = document.querySelector('#search-form');
    const excelDownloadButton = document.querySelector('#excel-download-button');
    const excelUploadButton = document.querySelector('#excel-upload-button');

    const confirmModal = createConfirmModal();

    checkbox.init(checkboxGroup);

    /* 품목 목록 엑셀 다운로드 */
    excelDownloadButton.addEventListener('click', async function () {
        const params = new URLSearchParams(new FormData(searchForm));

        window.location.href = `/api/items/excel/download?${params.toString()}`;
    });

    /* 품목 삭제 */
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
                    const response = await ajax.delete('/items', selectedIds);

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
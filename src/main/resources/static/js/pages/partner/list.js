import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createConfirmModal from "../../common/modal/confirm.js";
import toast from "../../common/toast.js";
import modal from "../../common/modal/modal.js";

document.addEventListener('DOMContentLoaded', function () {


    const checkboxGroup = document.querySelector('#partner-table');
    const deleteButton = document.querySelector('#partner-delete-button');
    const searchForm = document.querySelector('#search-form');
    const excelDownloadButton = document.querySelector('#excel-download-button');
    const excelUploadButton = document.querySelector('#excel-upload-button');

    const confirmModal = createConfirmModal();

    checkbox.init(checkboxGroup);

    /* 거래처 목록 엑셀 다운로드 */
    excelDownloadButton.addEventListener('click', async function () {
        const params = new URLSearchParams(new FormData(searchForm));

        window.location.href = `/api/partners/excel/download?${params.toString()}`;
    });

    /* 거래처 목록 엑셀 업로드 */
    excelUploadButton.addEventListener('click', function () {
        modal.open('partner-excel-upload-modal');
    });

    $('#excel-upload-form').validate({
        rules: {
            file: {
                required: true,
                extension: 'xlsx',
            }
        }, messages: {
            file: {
                required: '업로드할 엑셀 파일을 선택해 주세요.',
                extension: 'xlsx 형식의 엑셀 파일만 업로드할 수 있습니다.'
            }
        }, submitHandler: function (form) {
            handleSubmit(form.action, new FormData(form));
            return false;
        }
    });

    const handleSubmit = async (url, data) => {
        try {
            const response = await ajax.post(url, data);

            toast.afterReload({
                message: response.message,
            });

            modal.close('partner-excel-upload-modal');

            window.location.reload();

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON?.message ?? '처리 중 오류가 발생하였습니다.',
            });
        }
    }

    modal.resetFormOnHidden('partner-excel-upload-modal');

    /* 거래처 삭제 */
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
                    const response = await ajax.delete('/partners', selectedIds);

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
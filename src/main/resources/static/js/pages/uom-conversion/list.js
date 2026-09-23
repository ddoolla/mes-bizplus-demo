import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createUomConversionNewFormModal from "../../domain/uom-conversion/modal/form/new.js";
import createUomConversionEditFormModal from "../../domain/uom-conversion/modal/form/edit.js";
import toast from "../../common/toast.js";
import createConfirmModal from "../../common/modal/confirm.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#uom-conversion-table');
    const deleteButton = document.querySelector('#uom-conversion-delete-button');
    const createButton = document.querySelector('#uom-conversion-create-button');
    const editLinks = document.querySelectorAll('.uom-conversion-edit-link');

    const confirmModal = createConfirmModal();
    const uomConversionNewFormModal = createUomConversionNewFormModal();
    const uomConversionEditFormModal = createUomConversionEditFormModal()

    /* 단위 환산 테이블 체크박스 초기화 */
    checkbox.init(checkboxGroup);

    /* 단위 환산 등록 모달 */
    createButton.addEventListener('click', function () {
        uomConversionNewFormModal.open();
    });

    /* 단위 환산 수정 모달 */
    editLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            const uomConversionId = e.currentTarget.dataset.id;

            uomConversionEditFormModal.open(uomConversionId);
        });
    });

    /* 단위 환산 삭제 */
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
                    const response = await ajax.delete('/uoms/conversions', selectedIds);

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
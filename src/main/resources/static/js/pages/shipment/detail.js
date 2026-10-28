import createConfirmModal from "../../common/modal/confirm.js";
import ajax from "../../common/ajax.js";
import toast from "../../common/toast.js";

document.addEventListener('DOMContentLoaded', function () {

    const completeBtn = document.querySelector('#shipment-complete-button');

    const confirmModal = createConfirmModal();

    completeBtn.addEventListener('click', function (e) {
        const {id} = e.currentTarget.dataset;

        confirmModal.open({
            title: '출고 완료 확인',
            content: '출고를 완료하시겠습니까?\n완료 후 수정할 수 없습니다.',
            onConfirm: async () => {
                try {
                    const response = await ajax.patch(`/api/shipments/${id}/complete`);

                    toast.afterReload({
                        message: response.message
                    });

                    location.reload();

                } catch (xhr) {
                    toast.error({
                        message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
                    });
                }

            }
        })
    });
});
import createConfirmModal from "../../common/modal/confirm.js";
import ajax from "../../common/ajax.js";
import toast from "../../common/toast.js";

document.addEventListener('DOMContentLoaded', function () {

    const completeBtn = document.querySelector('#receipt-complete-button');

    const confirmModal = createConfirmModal();

    completeBtn.addEventListener('click', function (e) {
        const {id} = e.currentTarget.dataset;

        confirmModal.open({
            title: '입고 완료 확인',
            content: '입고를 완료하시겠습니까?\n확정 이후 입고 자재 정보를 변경할 수 없습니다.',
            onConfirm: async () => {
                try {
                    const response = await ajax.post(`/api/receipts/${id}/complete`);

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
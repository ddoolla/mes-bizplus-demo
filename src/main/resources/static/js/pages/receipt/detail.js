import createConfirmModal from "../../common/modal/confirm.js";
import ajax from "../../common/ajax.js";
import toast from "../../common/toast.js";

document.addEventListener('DOMContentLoaded', function () {

    const confirmBtn = document.querySelector('#receipt-confirm-button');

    const confirmModal = createConfirmModal();

    confirmBtn.addEventListener('click', function (e) {
        const {id} = e.currentTarget.dataset;

        confirmModal.open({
            title: '입고 확정 확인',
            content: '입고를 확정하시겠습니까?\n완료 후 수정할 수 없습니다.',
            onConfirm: async () => {
                try {
                    const response = await ajax.post(`/api/receipts/${id}/confirm`);

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
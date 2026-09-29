import createConfirmModal from "../../common/modal/confirm.js";
import ajax from "../../common/ajax.js";
import toast from "../../common/toast.js";

document.addEventListener('DOMContentLoaded', function () {

    const confirmBtn = document.querySelector('#sales-order-confirm-button');

    const confirmModal = createConfirmModal();

    confirmBtn.addEventListener('click', function (e) {
        const {id} = e.currentTarget.dataset;

        confirmModal.open({
            title: '수주 확정 확인',
            content: '수주를 확정하시겠습니까?\n확정 이후 품목 추가, 삭제 및 수량, 단가를 변경할 수 없습니다.',
            onConfirm: async () => {
                try {
                    const response = await ajax.patch(`/sales-orders/${id}/confirm`);

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
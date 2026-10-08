import createConfirmModal from "../../common/modal/confirm.js";
import ajax from "../../common/ajax.js";
import toast from "../../common/toast.js";

document.addEventListener('DOMContentLoaded', function () {

    const confirmBtn = document.querySelector('#production-order-confirm-button');

    const confirmModal = createConfirmModal();

    confirmBtn.addEventListener('click', function (e) {
        const {id, itemId} = e.currentTarget.dataset;

        confirmModal.open({
            title: '생산지시 확정 확인',
            content: '생산지시를 확정하시겠습니까?\n확정 이후 생산 예정일 및 수량을 변경할 수 없습니다.',
            onConfirm: async () => {
                try {
                    const response = await ajax.patch(`/production-orders/${id}/confirm`, {itemId});

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
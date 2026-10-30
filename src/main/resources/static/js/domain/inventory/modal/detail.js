import ajax from "../../../common/ajax.js";
import toast from "../../../common/toast.js";
import modal from "../../../common/modal/modal.js";

const MODAL_ID = 'inventory-lot-list-modal';

const createInventoryDetailModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const contentEl = modalEl.querySelector('.app-modal-content');

    const load = async ({url, params = {}}) => {
        try {
            contentEl.innerHTML = await ajax.get(url, params);

            return true;

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON.message,
            });

            return false;
        }
    };

    // 모달 열기
    const open = async ({title, url, params}) => {
        const success = await load({url, params});

        if (!success) {
            return;
        }

        modal.setTitle(MODAL_ID, title);
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    return {
        open,
        close,
    };
}

export default createInventoryDetailModal;
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

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON.message,
            });
        }
    };

    // 모달 열기
    const open = ({
                      title = 'LOT별 재고 목록',
                      url,
                      params = {},
                  }) => {
        modal.setTitle(MODAL_ID, title);
        load({url, params});
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
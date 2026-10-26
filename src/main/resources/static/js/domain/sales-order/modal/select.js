import ajax from "../../../common/ajax.js";
import toast from "../../../common/toast.js";
import modal from "../../../common/modal/modal.js";

const MODAL_ID = 'sales-order-select-modal';

const createSalesOrderSelectModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const contentEl = modalEl.querySelector('.app-modal-content');

    let selectCallback = null;

    const load = async (url, params) => {
        contentEl.innerHTML = await ajax.get(url, params);
    };

    // 모달 열기
    const open = async ({title = '수주 선택', url, params, onSelect}) => {
        selectCallback = onSelect ?? null;

        try {
            modal.setTitle(MODAL_ID, title);
            await load(url, params);
            modal.open(MODAL_ID);

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON.message ?? '수주 목록을 불러오지 못했습니다.',
            });
        }
    };


    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    contentEl.addEventListener('click', function (e) {
        const button = e.target.closest('.sales-order-select-button');

        if (!button) {
            return;
        }

        const {id, orderNo} = button.dataset;

        selectCallback?.({id, orderNo});
        close();
    });

    return {
        open,
        close,
    };
}

export default createSalesOrderSelectModal;
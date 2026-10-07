import ajax from "../../../../common/ajax.js";
import modal from "../../../../common/modal/modal.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'process-material-list-modal';

const createProcessMaterialListModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const contentEl = modalEl.querySelector('.process-material-list-content');

    const render = (html) => {
        contentEl.innerHTML = html;
    };

    const load = async (url) => {
        const response = await ajax.get(url);
        render(response);
    };

    const open = async (title = '소모 자재 목록', contentUrl) => {
        modal.setTitle(MODAL_ID, title);

        try {
            await load(contentUrl);

            modal.open(MODAL_ID);

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
            });
        }
    };

    return {
        open,
    };
}

export default createProcessMaterialListModal;

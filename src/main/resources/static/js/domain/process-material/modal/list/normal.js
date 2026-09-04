import ajax from "../../../../common/ajax.js";
import modal from "../../../../common/modal/modal.js";

const createProcessMaterialListModal = () => {

    const modalId = 'process-material-list-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const contentEl = modalEl.querySelector('.process-material-list-content');

    const render = (html) => {
        contentEl.innerHTML = html;
    };

    const load = async (url) => {
        const response = await ajax.get(url);

        render(response);
    };

    const open = async (title = '소모 자재 목록', contentUrl) => {
        modal.setTitle(modalId, title);

        try {
            await load(contentUrl);

            modal.open(modalId);

        } catch (xhr) {
            alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
        }
    };

    return {
        open,
    };
}

export default createProcessMaterialListModal;

import ajax from "../../../common/ajax.js";
import toast from "../../../common/toast.js";
import modal from "../../../common/modal/modal.js";

const MODAL_ID = 'inspection-spec-select-modal';

const createInspectionSpecSelectModal = () => {

    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const listContent = modalEl.querySelector('.list-content');

    let selectCallback = null;

    const render = (response) => {
        listContent.innerHTML = response;
    };

    const load = async ({url, params}) => {
        try {
            const response = await ajax.get(url, params);
            render(response);

            return true;

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON?.message ?? '검사기준 목록 조회에 실패했습니다.',
            });

            return false;
        }
    };

    // 모달 열기
    const open = async ({title, url, params, onSelect}) => {
        const success = await load({url, params});

        if (!success) {
            return;
        }

        selectCallback = onSelect ?? null;

        modal.setTitle(MODAL_ID, title);
        await load({url, params});
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 선택 이벤트
    listContent.addEventListener('click', function (e) {
        const button = e.target.closest('.select-button');

        if (!button) {
            return;
        }

        const inspectionSpec = {
            id: button.dataset.id,
            name: button.dataset.name,
        };

        selectCallback?.(inspectionSpec);
    });

    return {
        open,
        close,
    };
}
export default createInspectionSpecSelectModal;
import checkbox from "../../../common/checkbox.js";
import ajax from "../../../common/ajax.js";
import toast from "../../../common/toast.js";
import modal from "../../../common/modal/modal.js";

const MODAL_ID = 'inventory-lot-multi-select-modal';

const createInventoryLotMultiSelectModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const contentEl = modalEl.querySelector('.app-modal-content');

    let registerCallback = null;
    let backCallback = null;

    const render = (response) => {
        contentEl.innerHTML = response;
        checkbox.init(contentEl);
    };

    const load = async ({url, params}) => {
        try {
            const response = await ajax.get(url, params);
            render(response);

            return true;

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON.message,
            });

            return false;
        }
    };

    // 모달 열기
    const open = async ({title, url, params, onBack, onRegister}) => {
        const success = await load({url, params});

        if (!success) {
            return;
        }

        registerCallback = onRegister ?? null;
        backCallback = onBack ?? null;

        modal.setTitle(MODAL_ID, title);
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 목록 버튼
    contentEl.addEventListener('click', function (e) {
        const button = e.target.closest('.back-button');

        if (!button) {
            return;
        }

        backCallback?.();
    });

    // 모달 등록 버튼 클릭
    contentEl.addEventListener('click', function (e) {
        const button = e.target.closest('.lot-register-button');

        if (!button) {
            return;
        }

        const selectedIds = checkbox.getCheckedValues(contentEl);

        if (!selectedIds.length) {
            toast.error({
                message: '항목을 선택해 주세요.'
            });

            return;
        }

        registerCallback?.(selectedIds);
    });

    return {
        open,
        close,
    };
};

export default createInventoryLotMultiSelectModal;
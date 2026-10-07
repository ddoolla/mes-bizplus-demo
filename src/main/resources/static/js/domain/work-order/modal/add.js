import modal from "../../../common/modal/modal.js";
import checkbox from "../../../common/checkbox.js";
import toast from "../../../common/toast.js";

const MODAL_ID = 'work-order-add-modal';

const createWorkOrderAddModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const popList = modalEl.querySelector('.production-order-process-list');

    let registerCallback = null;

    checkbox.init(popList);

    // 모달 열기
    const open = ({title = '생산지시 공정 목록', onRegister}) => {
        registerCallback = onRegister ?? null;

        modal.setTitle(MODAL_ID, title);
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(MODAL_ID);

    // 등록 버튼 클릭
    popList.addEventListener('click', function (e) {
        const createBtn = e.target.closest('.create-confirm-button');

        if (!createBtn) {
            return;
        }

        const selectedIds = checkbox.getCheckedValues(popList);

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

export default createWorkOrderAddModal;
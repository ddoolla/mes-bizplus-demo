import modal from "../../../common/modal/modal.js";
import checkbox from "../../../common/checkbox.js";
import toast from "../../../common/toast.js";

const createWorkOrderAddModal = () => {

    const modalId = 'work-order-add-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const popList = modalEl.querySelector('.production-order-process-list');

    checkbox.init(popList);

    // 모달 열기
    const open = ({
                      title = '생산지시 공정 목록',
                      onRegister,
                  }) => {
        modal.setTitle(modalId, title);

        if (onRegister) {
            handleRegister(onRegister);
        }

        modal.open(modalId);
    };

    // 모달 닫기
    const close = () => {
        modal.close(modalId);
    };

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(modalId);

    // 등록 이벤트
    const handleRegister = (onRegister) => {
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

            onRegister(selectedIds);
        });
    };

    return {
        open,
        close,
    };
};

export default createWorkOrderAddModal;
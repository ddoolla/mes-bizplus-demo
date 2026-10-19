import modal from "../../../common/modal/modal.js";
import ajax from "../../../common/ajax.js";
import toast from "../../../common/toast.js";

const MODAL_ID = 'material-input-modal';

const createMaterialInputModal2 = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const modalContent = modalEl.querySelector('.app-modal-content');
    const itemSelectButtons = modalContent.querySelectorAll('.item-select-button');

    const load = async (url, params) => {
        try {
            modalContent.innerHTML = await ajax.get(url, params);

        } catch (xhr) {
            toast.error(xhr.responseJSON?.message);
        }
    }
    // 모달 열기

    const open = ({title, url, params}) => {
        modal.setTitle(MODAL_ID, title);
        load(url, params);
        modal.open(MODAL_ID);
    };
    // 모달 닫기

    const close = () => {
        modal.close(MODAL_ID);
    };

    // 소모 자재 선택
    modalContent.addEventListener('click', function (e) {
        const button = e.target.closest('.item-select-button');

        if (!button || !modalContent.contains(button)) {
            return;
        }

        const {
            itemId,
            itemCode,
            itemName,
            itemSpec,
            requiredQuantity,
            inputQuantity,
            remainingQuantity
        } = button.dataset;

        modal.setTitle(MODAL_ID, 'ㅇㅇㅇ');

        load('/material-inputs/modal/input/material-lot/selection');
    });

    return {
        open,
        close,
    };
};

export default createMaterialInputModal2;
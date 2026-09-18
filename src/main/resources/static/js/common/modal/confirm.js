import modal from "./modal.js";

const createConfirmModal = () => {
    const modalId = 'confirm-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const contentEl = modalEl.querySelector('.modal-body p');
    const confirmButton = modalEl.querySelector('.confirm-button');

    const open = ({title, content, onConfirm}) => {
        modal.setTitle(modalId, title);

        contentEl.textContent = content;

        confirmButton.onclick = () => {
            onConfirm();
            modal.close(modalId);
        };

        modal.open(modalId);
    };

    return {
        open
    };
};

export default createConfirmModal;
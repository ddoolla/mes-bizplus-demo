import modal from "./modal.js";

const createImageViewerModal = () => {
    const modalId = 'image-viewer-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const modalImageEl = modalEl.querySelector('.modal-image');

    const open = ({title, url}) => {
        modal.setTitle(modalId, title);

        modalImageEl.src = url;

        modal.open(modalId);
    };

    return {
        open
    };
};

export default createImageViewerModal;
import createImageModal from "../../common/modal/image-modal.js";

document.addEventListener('DOMContentLoaded', function () {

    const itemImages = document.querySelectorAll('.item-image');
    const imageModal = createImageModal();

    itemImages.forEach(itemImage => {
        itemImage.addEventListener('click', function (e) {
            const {fileId, fileName} = e.currentTarget.dataset;
            console.log(fileId, fileName);

            imageModal.open({title: fileName, url: `/files/${fileId}`});
        });
    });
});
import createImageViewerModal from "../../common/modal/image-viewer.js";

document.addEventListener('DOMContentLoaded', function () {

    const itemImages = document.querySelectorAll('.item-image');
    const imageViewerModal = createImageViewerModal();

    itemImages.forEach(itemImage => {
        itemImage.addEventListener('click', function (e) {
            const {fileId, fileName} = e.currentTarget.dataset;
            console.log(fileId, fileName);

            imageViewerModal.open({title: fileName, url: `/files/${fileId}`});
        });
    });
});
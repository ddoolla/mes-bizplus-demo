import createInventoryDetailModal from "../../domain/inventory/modal/detail.js";

document.addEventListener('DOMContentLoaded', function () {

    const detailButtons = document.querySelectorAll('.inventory-detail-button');

    const inventoryDetailModal = createInventoryDetailModal();

    detailButtons.forEach(button => {
        button.addEventListener('click', function (e) {
            const {itemId} = e.currentTarget.dataset;

            inventoryDetailModal.open({
                title: 'LOT별 재고 목록',
                url: `/inventories/modal/detail`,
                params: {itemId: itemId}
            });
        });
    })
});
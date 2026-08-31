import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createBomItemMultipleListModal from "../../domain/bom-item/modal/list/multiple.js";
import createItemMultipleListModal from "../../domain/item/modal/list/multiple.js";
import tooltip from "../../common/tooltip.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#process-material-table');
    const deleteButton = document.querySelector('#process-material-delete-button');

    const materialListButton = document.querySelector('#btn-add-from-item');
    const bomItemListButton = document.querySelector('#btn-add-from-bom');

    const itemMultipleListModal = createItemMultipleListModal();
    const bomItemMultipleListModal = createBomItemMultipleListModal();

    /* 툴팁 */
    tooltip.init();

    /* 자재 목록 모달 */
    materialListButton.addEventListener('click', function () {
        itemMultipleListModal.open(
            '자재 목록',
            '/items/bom_item/modal/list/multiple'
        );
    });

    itemMultipleListModal.onRegister(async (selectedIds) => {
        const {routingProcessId} = materialListButton.dataset;

        try {
            const response = await ajax.post(
                `/routing-processes/${routingProcessId}/materials/from-items`,
                {itemIds: selectedIds},
            );

            alert(response.message);

            location.reload();

            itemMultipleListModal.close();

        } catch (xhr) {
            alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
        }
    });

    /* BOM 구성품 목록 모달 */
    bomItemListButton.addEventListener('click', function (e) {
        const {primaryBomId, itemName} = bomItemListButton.dataset;

        const params = primaryBomId
            ? new URLSearchParams({bomId: primaryBomId}).toString()
            : '';

        bomItemMultipleListModal.open(
            `BOM 구성품 목록 - ${itemName}`,
            '/bom-items/modal/list/multiple',
            params
        );
    });

    bomItemMultipleListModal.onRegister(async (selectedIds) => {
        const {routingProcessId} = bomItemListButton.dataset;

        try {
            const response = await ajax.post(
                `/routing-processes/${routingProcessId}/materials/from-boms`,
                {bomIds: selectedIds},
            );

            alert(response.message);

            location.reload();

            bomItemMultipleListModal.close();

        } catch (xhr) {
            alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
        }
    });

    /* 공정 소모 자재 삭제 */
    checkbox.init(checkboxGroup);

    deleteButton.addEventListener('click', async function () {
        const selectedIds = checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {
            alert('항목을 선택해 주세요.');
            return;
        }

        if (!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        deleteButton.disabled = true;

        try {
            const response = await ajax.delete('/process-materials', selectedIds);

            alert(response.message);

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON.message || '처리중 오류가 발생했습니다.');
        }
    });
});
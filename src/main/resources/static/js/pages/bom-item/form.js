import ajax from "../../common/ajax.js";
import checkbox from "../../common/checkbox.js";
import createItemMultiSelectModal from "../../domain/item/modal/multi-select.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#bom-item-table');
    const deleteButton = document.querySelector('#bom-item-delete-button');

    const bomEditForm = document.querySelector('#bom-edit-form');
    const itemMultiSelectModalButton = document.querySelector('#bom-item-create-button');

    const itemMultiSelectModal = createItemMultiSelectModal();

    // BOM 구성 품목 등록
    itemMultiSelectModalButton.addEventListener('click', function () {
        itemMultiSelectModal.open(
            'BOM 구성품 목록',
            `/items/bom_item/modal/select/multi`
        );
    });

    itemMultiSelectModal.onRegister(async function (selectedIds) {
        const bomId = bomEditForm.querySelector('[name="id"]').value;

        try {
            const response = await ajax.post(
                `/boms/${bomId}/items`,
                {itemIds: selectedIds}
            );

            alert(response.message);

            location.reload();

            itemMultiSelectModal.close();

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    });

    // BOM 구성 품목 삭제
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
            const response = await ajax.delete('/bom-items', selectedIds);

            alert(response.message);

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    });
});
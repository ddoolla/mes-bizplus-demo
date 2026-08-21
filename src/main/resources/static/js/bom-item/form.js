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
            `/items/modal/bom_item/multi-select-list`
        );
    });

    itemMultiSelectModal.onRegister(function (selectedIds) {
        const bomId = bomEditForm.querySelector('[name="id"]').value;

        Mes.Ajax.post(`/boms/${bomId}/items`, {itemIds: selectedIds})
            .done(function (response) {
                alert(response.message);

                location.reload();

                itemMultiSelectModal.close();
            })
            .fail(function (xhr) {
                alert(xhr.responseJSON.message);
            });
    });

    // BOM 구성 품목 삭제
    Mes.Checkbox.init(checkboxGroup);

    deleteButton.addEventListener('click', function () {

        const selectedIds = Mes.Checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {

            alert('항목을 선택해 주세요.');
            return;
        }

        if (!confirm('정말 삭제하시겠습니까?')) {
            return;
        }

        deleteButton.disabled = true;

        Mes.Ajax.delete('/bom-items', selectedIds)
            .done(function (response) {

                alert(response.message);

                location.reload();
            })
            .fail(function (xhr) {

                alert(xhr.responseJSON.message);

                location.reload();
            });
    });
});
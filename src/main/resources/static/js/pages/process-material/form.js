import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createBomItemMultipleListModal from "../../domain/bom-item/modal/list/multiple.js";
import createItemMultipleListModal from "../../domain/item/modal/list/multiple.js";
import tooltip from "../../common/tooltip.js";
import toast from "../../common/toast.js";
import createConfirmModal from "../../common/modal/confirm.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#process-material-table');
    const deleteButton = document.querySelector('#process-material-delete-button');

    const materialListButton = document.querySelector('#btn-add-from-item');
    const bomItemListButton = document.querySelector('#btn-add-from-bom');

    const confirmModal = createConfirmModal();
    const itemMultipleListModal = createItemMultipleListModal();
    const bomItemMultipleListModal = createBomItemMultipleListModal();

    /* 툴팁 */
    tooltip.init();

    /* 자재 목록 모달 */
    materialListButton.addEventListener('click', function () {
        itemMultipleListModal.open({
            title: '자재 목록',
            url: '/items/modal/list/multiple',
            params: {group: 'BOM_ITEM'},
            onRegister: async (selectedIds) => {
                const {routingProcessId} = materialListButton.dataset;

                try {
                    const response = await ajax.post(
                        `/routing-processes/${routingProcessId}/materials/from-items`,
                        {itemIds: selectedIds},
                    );

                    itemMultipleListModal.close();

                    toast.afterReload({
                        message: response.message,
                    });

                    location.reload();

                } catch (xhr) {
                    toast.error({
                        message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
                    });
                }
            }
        });
    });

    /* BOM 구성품 목록 모달 */
    bomItemListButton.addEventListener('click', function (e) {
        const {defaultBomId, itemName} = bomItemListButton.dataset;

        const params = defaultBomId
            ? new URLSearchParams({bomId: defaultBomId}).toString()
            : '';

        bomItemMultipleListModal.open({
            title: `BOM 구성품 목록 - ${itemName}`,
            url: '/bom-items/modal/list/multiple',
            params: params,
            onRegister: async (selectedIds) => {
                const {routingProcessId} = bomItemListButton.dataset;

                try {
                    const response = await ajax.post(
                        `/routing-processes/${routingProcessId}/materials/from-boms`,
                        {bomItemIds: selectedIds},
                    );

                    bomItemMultipleListModal.close();

                    toast.afterReload({
                        message: response.message,
                    });

                    location.reload();

                } catch (xhr) {
                    toast.error({
                        message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
                    });
                }
            }
        });
    });

    /* 공정 소모 자재 삭제 */
    checkbox.init(checkboxGroup);

    deleteButton.addEventListener('click', async function () {
        const selectedIds = checkbox.getCheckedValues(checkboxGroup);

        if (!selectedIds.length) {
            toast.error({
                message: '항목을 선택해 주세요.',
            });

            return;
        }

        confirmModal.open({
            title: '삭제 확인',
            content: '정말 삭제하시겠습니까?',
            onConfirm: async () => {
                try {
                    const response = await ajax.delete('/process-materials', selectedIds);

                    toast.afterReload({
                        message: response.message,
                    });

                    location.reload();

                } catch (xhr) {
                    toast.error({
                        message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
                    });
                }
            }
        });
    });
});
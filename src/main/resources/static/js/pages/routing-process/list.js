import createProcessMaterialListModal from "../../domain/process-material/modal/list/normal.js";

document.addEventListener('DOMContentLoaded', function () {

    const listButton = document.querySelectorAll('.process-material-list-button');

    const processMaterialListModal = createProcessMaterialListModal();

    listButton.forEach(button => {
        button.addEventListener('click', function (e) {
            const {routingProcessId, processCode, processName} = e.currentTarget.dataset;

            const title = `소모 자재 목록 - ${processCode} (${processName})`;
            const contentUrl = `/routing-processes/${routingProcessId}/materials/modal/list`;

            processMaterialListModal.open(title, contentUrl);
        });
    });
});
import modal from "../../common/modal/modal.js";

document.addEventListener('DOMContentLoaded', function () {

    const labelButtons = document.querySelectorAll('.lot-label-button');
    const labelPrintButton = document.querySelector('#label-print-button');

    // 라벨 출력 버튼 이벤트
    labelButtons.forEach(button => {
        button.addEventListener('click', function (e) {
            const {lotNo} = e.currentTarget.dataset;

            JsBarcode("#lot-barcode", lotNo);

            modal.open('lot-label-modal');
        });
    });

    // 인쇄 버튼 이벤트
    labelPrintButton.addEventListener('click', function (e) {
        window.print();
    });
});
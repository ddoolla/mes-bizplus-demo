import modal from "../../../common/modal/modal.js";
import ajax from "../../../common/ajax.js";
import toast from "../../../common/toast.js";

const createMaterialInputModal = () => {

    /* 투입 자재 선택 모달 */
    const materialSelectionModalEl = document.querySelector('#material-selection-modal');
    const materialSelectButtons = materialSelectionModalEl.querySelectorAll('.material-select-button');
    const workOrderId = materialSelectionModalEl.querySelector('[name="workOrderId"]').value;

    /* 자재 투입 모달 */
    const materialInputModalEl = document.querySelector('#material-input-modal');
    const backButton = materialInputModalEl.querySelector('.back-button');
    const lotScanSection = materialInputModalEl.querySelector('#lot-scan-section');
    const lotScanInput = materialInputModalEl.querySelector('input[name="lotNo"]');
    const inputQuantityInput = materialInputModalEl.querySelector('input[name="inputQuantity"]');
    const lotScanForm = materialInputModalEl.querySelector('#lot-scan-form');
    const lotInputForm = materialInputModalEl.querySelector('#lot-input-form');
    const lotNoField = materialInputModalEl.querySelector('input[data-field="lotNo"]');
    const lotQuantityField = materialInputModalEl.querySelector('input[data-field="lotQuantity"]');
    const inputQuantityErrorContainer = materialInputModalEl.querySelector('#input-quantity-error-container');

    let lotManaged = false;
    let selectedItem = null;
    let scannedLot = null;

    const openMaterialSelectionModal = () => {
        modal.setTitle('material-selection-modal', '투입 자재 목록');
        modal.open('material-selection-modal');
    }

    const openMaterialInputModal = () => {
        const unit = ' ' + selectedItem.uomCode;
        setMaterialInfo('itemCode', selectedItem.itemCode);
        setMaterialInfo('itemName', selectedItem.itemName);
        setMaterialInfo('itemSpec', selectedItem.itemSpec ?? '-');
        setMaterialInfo('requiredQuantity', Number(selectedItem.requiredQuantity)?.toLocaleString() + unit);
        setMaterialInfo('inputQuantity', Number(selectedItem.inputQuantity)?.toLocaleString() + unit);
        setMaterialInfo('remainingQuantity', Number(selectedItem.remainingQuantity)?.toLocaleString() + unit);
        setMaterialInfo('excessQuantity', Number(selectedItem.excessQuantity)?.toLocaleString() + unit);

        lotManaged = selectedItem.lotManaged === 'true';
        lotScanSection.style.display = lotManaged ? '' : 'none';

        modal.setTitle('material-input-modal', '자재 투입');
        modal.open('material-input-modal');
    }

    const setMaterialInfo = (field, value) => {
        materialInputModalEl.querySelector(`[data-field="${field}"]`).textContent = value;
    }

    const setInputQuantityError = (message = '') => {
        inputQuantityInput.classList.toggle('is-invalid', !!message);
        inputQuantityErrorContainer.textContent = message;
    };

    const closeMaterialSelectionModal = () => {
        modal.close('material-selection-modal');
    }

    const closeMaterialInputModal = () => {
        modal.close('material-input-modal');
    }

    // 모달 열림 시 포커싱
    materialInputModalEl.addEventListener('shown.bs.modal', () => {
        if (lotManaged) {
            lotScanInput.focus();

        } else {
            inputQuantityInput.focus();
        }
    });

    // 투입 자재 선택
    materialSelectButtons.forEach(button => {
        button.addEventListener('click', function (e) {
            selectedItem = e.currentTarget.dataset;
            closeMaterialSelectionModal();
            openMaterialInputModal();
        });
    });

    // 투입 자재 목록으로 돌아가기
    backButton.addEventListener('click', function () {
        closeMaterialInputModal();
        openMaterialSelectionModal();
    });

    // 자재 로트 스캔
    lotScanForm.addEventListener('submit', async function (e) {
        e.preventDefault();

        const lotNo = lotScanInput.value.trim();
        const inputUomId = selectedItem.uomId;

        if (!lotNo) {
            lotScanInput.focus();
            return;
        }

        // 기존 스캔 정보 초기화
        scannedLot = null;
        lotNoField.value = '';
        lotQuantityField.value = '';
        inputQuantityInput.value = '';
        lotScanInput.classList.remove('is-valid', 'is-invalid');

        try {
            const response = await ajax.get('/api/material-inputs/lot', {lotNo, inputUomId});
            scannedLot = response?.data;

            // 다른 자재의 LOT인지 확인
            if (String(scannedLot?.itemId) !== String(selectedItem.itemId)) {
                lotScanInput.classList.add('is-invalid');
                toast.warning({message: '선택한 자재의 LOT가 아닙니다.'});
                lotScanInput.focus();
                return;
            }

            const lotQuantity = Number(scannedLot?.quantity);
            const remainingQuantity = Number(selectedItem.remainingQuantity);
            const inputUnit = scannedLot.inputUomCode;

            lotNoField.value = scannedLot.no ?? '-';
            lotQuantityField.value = (lotQuantity.toLocaleString() ?? '-') + ' ' + inputUnit;

            inputQuantityInput.value = Math.min(lotQuantity, remainingQuantity);

            lotScanInput.classList.add('is-valid');
            inputQuantityInput.focus();

        } catch (xhr) {
            lotScanInput.classList.add('is-invalid');

            toast.error({
                message: xhr.responseJSON?.message ?? 'LOT 조회에 실패했습니다.'
            });

            lotScanInput.focus();
        }
    });

    // 자재 투입
    inputQuantityInput.addEventListener('input', function (e) {
        setInputQuantityError();

        const inputQty = inputQuantityInput.value
        const lotQty = scannedLot.quantity

        inputQuantityInput.value = inputQty > lotQty ? lotQty : inputQty;
    });

    lotInputForm.addEventListener('submit', async function (e) {
        e.preventDefault();

        const inputQuantity = Number(inputQuantityInput.value);

        if (lotManaged && !scannedLot) {
            toast.warning({message: '로트 스캔 정보를 확인해 주세요.'});
            lotScanInput.focus();
            return;
        }

        if (!inputQuantity) {
            setInputQuantityError('투입 수량을 입력해 주세요.');
            inputQuantityInput.focus();
            return;
        }

        if (!Number.isFinite(inputQuantity) || inputQuantity <= 0) {
            setInputQuantityError('투입 수량은 0보다 커야 합니다.');
            inputQuantityInput.focus();
            return;
        }

        const data = {
            workOrderId: workOrderId,
            itemId: selectedItem.itemId,
            lotId: scannedLot.id,
            uomId: selectedItem.uomId,
            type: 'MANUAL',
            quantity: inputQuantity
        };

        try {
            const response = await ajax.post(`/api/material-inputs`, data);

            toast.afterReload({message: response.message});

            closeMaterialInputModal();

            location.reload();

        } catch (xhr) {
            toast.error({message: xhr.responseJSON?.message});
        }
    });

    // 모달 닫힘 시 초기화
    materialInputModalEl.addEventListener('hidden.bs.modal', () => {
        scannedLot = null;
        lotScanForm.reset();
        lotInputForm.reset();
        lotScanInput.classList.remove('is-valid', 'is-invalid');
        setInputQuantityError();
        lotNoField.value = '';
        lotQuantityField.value = '';
    });

    return {
        open: () => openMaterialSelectionModal(),
    }
};

export default createMaterialInputModal;
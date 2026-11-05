import modal from "../../../common/modal/modal.js";

const MODAL_ID = 'work-result-input-modal';

const createWorkResultInputModal = () => {
        const modalEl = document.querySelector(`#${MODAL_ID}`);
        const formEl = modalEl.querySelector('#work-result-input-form');

        const prodQtyInput = formEl.elements['productionQuantity'];
        const goodQtyInput = formEl.elements['goodQuantity'];
        const defectQtyInput = formEl.elements['defectQuantity'];

        let submitCallback = null;

        // 수량 설정
        const setQuantity = ({prodQty, goodQty, defectQty}) => {
            prodQtyInput.value = prodQty ?? 0;
            goodQtyInput.value = goodQty ?? 0;
            defectQtyInput.value = defectQty ?? 0;
        }

        const open = async ({title, onSubmit, prodQty, goodQty, defectQty}) => {
            submitCallback = onSubmit ?? null;

            setQuantity({prodQty, goodQty, defectQty});

            modal.setTitle(MODAL_ID, title);
            modal.open(MODAL_ID);
        };

        const close = () => {
            modal.close(MODAL_ID);
        };

        // 모달 닫기 시 폼 초기화
        modal.resetFormOnHidden(MODAL_ID);

        // 총 생산 수량 변경
        prodQtyInput.addEventListener('input', () => {
            if (prodQtyInput.value === '') {
                goodQtyInput.value = '';
                return;
            }

            const prodQty = Number(prodQtyInput.value);
            const defectQty = Number(defectQtyInput.value);

            goodQtyInput.value = Math.max(0, prodQty - defectQty);
        });

        // 양품 수량 변경
        goodQtyInput.addEventListener('input', () => {
            if (goodQtyInput.value === '') {
                defectQtyInput.value = '';
                return;
            }

            const prodQty = Number(prodQtyInput.value);
            const goodQty = Number(goodQtyInput.value);

            defectQtyInput.value = Math.max(0, prodQty - goodQty);
        });

        // 불량 수량 변경
        defectQtyInput.addEventListener('input', () => {
            if (defectQtyInput.value === '') {
                goodQtyInput.value = '';
                return;
            }

            const prodQty = Number(prodQtyInput.value);
            const defectQty = Number(defectQtyInput.value);

            goodQtyInput.value = Math.max(0, prodQty - defectQty);
        });

        // 수량 합계 검증
        $.validator.addMethod('quantityMatch', function () {
            const prodQty = Number(prodQtyInput.value);
            const goodQty = Number(goodQtyInput.value);
            const defectQty = Number(defectQtyInput.value);

            return prodQty === goodQty + defectQty;
        }, '총 생산 수량은 양품 수량과 불량 수량의 합계와 같아야 합니다.');

        $(formEl).validate({
            rules: {
                productionQuantity: {
                    required: true,
                    number: true,
                    positive: true,
                    quantityMatch: true,
                },
                goodQuantity: {
                    required: true,
                    number: true,
                    min: 0,
                },
                defectQuantity: {
                    required: true,
                    number: true,
                    min: 0,
                },
            },
            messages: {
                productionQuantity: {
                    required: '총 생산 수량을 입력해 주세요',
                    number: '숫자만 입력해 주세요.',
                    min: '0 보다 큰 값을 입력해 주세요.',
                },
                goodQuantity: {
                    required: '양품 수량을 입력해 주세요',
                    number: '숫자만 입력해 주세요.',
                    min: '0 이상 입력해 주세요.',
                },
                defectQuantity: {
                    required: '불량 수량을 입력해 주세요',
                    number: '숫자만 입력해 주세요.',
                    min: '0 이상 입력해 주세요.',
                },
            },
            submitHandler: function (form) {
                submitCallback?.(form);

                return false;
            }
        });

        return {
            open,
            close,
        };
    }
;

export default createWorkResultInputModal;
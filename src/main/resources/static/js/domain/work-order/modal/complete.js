import modal from "../../../common/modal/modal.js";
import ajax from "../../../common/ajax.js";
import toast from "../../../common/toast.js";

const MODAL_ID = 'work-complete-modal';

const createWorkCompleteModal = () => {
        const modalEl = document.querySelector(`#${MODAL_ID}`);
        const workCompeteForm = modalEl.querySelector('#work-complete-form');

        const onSubmit = async (form) => {
            const formData = new FormData(form);

            try {
                const response = await ajax.patch(form.action, formData);

                modal.close(MODAL_ID);

                toast.afterReload({
                    message: response.message,
                });

                location.reload();

            } catch (xhr) {
                toast.error({
                    message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
                });
            }
        };

        $(workCompeteForm).validate({
            rules: {
                productionQuantity: {
                    required: true,
                    number: true,
                    min: 0,
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
                    min: '0 이상 입력해 주세요.',
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
                onSubmit(form);

                return false;
            }
        });

        // 모달 닫기 시 폼 초기화
        modal.resetFormOnHidden(MODAL_ID);

        const open = async () => {
            modal.setTitle(MODAL_ID, '작업 실적 등록');
            modal.open(MODAL_ID);
        };

        return {
            open,
        };
    }
;

export default createWorkCompleteModal;
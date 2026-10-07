import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'inspection-item-form-modal';
const CONTENT_URL = '/inspection-items/modal/form/new';

const createInspectionItemNewFormModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const contentEl = modalEl.querySelector('.inspection-item-form-content');

    const onSubmit = async (form) => {
        const formData = new FormData(form);

        try {
            const response = await ajax.post(form.action, formData);

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

    const initFormValidate = () => {
        const formEl = contentEl.querySelector('#inspection-item-new-form')

        $(formEl).validate({
            rules: {
                code: {
                    required: true,
                    remote: {
                        url: '/inspection-items/check-code',
                        type: 'get',
                    },
                },
                name: 'required',
            },
            messages: {
                code: {
                    required: '검사항목 코드를 입력해 주세요.',
                    remote: '이미 존재하는 검사항목 코드입니다.'
                },
                name: '검사항목명을 입력해 주세요.',
            },
            submitHandler: function (form) {
                onSubmit(form);

                return false;
            }
        });
    };

    const render = (html) => {
        contentEl.innerHTML = html;
        initFormValidate();
    };

    const load = async () => {
        const response = await ajax.get(CONTENT_URL);

        render(response);
    };

    const open = async () => {
        modal.setTitle(MODAL_ID, '검사항목 등록');

        try {
            await load();

            modal.open(MODAL_ID);

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
            });
        }
    };

    return {
        open,
    };
}

export default createInspectionItemNewFormModal;
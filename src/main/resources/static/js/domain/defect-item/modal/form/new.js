import modal from "../../../../common/modal.js";
import ajax from "../../../../common/ajax.js";

const createDefectItemNewFormModal = () => {

    const modalId = 'defect-item-form-modal';
    const contentUrl = '/defect-items/modal/form/new';

    const modalEl = document.querySelector(`#${modalId}`);
    const contentEl = modalEl.querySelector('.defect-item-form-content');

    const onSubmit = async (form) => {
        const formData = new FormData(form);

        try {
            const response = await ajax.post(form.action, formData);

            alert(response.message);

            modal.close(modalId);

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
        }
    };

    const initFormValidate = () => {
        const formEl = contentEl.querySelector('#defect-item-create-form')

        $(formEl).validate({
            rules: {
                code: {
                    required: true,
                    remote: {
                        url: '/defect-items/check-code',
                        type: 'get',
                    },
                },
                name: 'required',
            },
            messages: {
                code: {
                    required: '불량 코드를 입력해 주세요.',
                    remote: '이미 존재하는 불량 코드입니다.'
                },
                name: '불량명을 입력해 주세요.',
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
        const response = await ajax.get(contentUrl);

        render(response);
    };

    const open = async () => {
        modal.setTitle(modalId, '불량항목 등록');

        try {
            await load();

            modal.open(modalId);

        } catch (xhr) {
            alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
        }
    };

    return {
        open,
    };
}

export default createDefectItemNewFormModal;
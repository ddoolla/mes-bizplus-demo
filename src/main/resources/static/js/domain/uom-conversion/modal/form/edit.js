import ajax from "../../../../common/ajax.js";
import modal from "../../../../common/modal.js";
import "../../../../common/validation.js";

const createUomConversionEditModal = () => {

    const modalId = 'uom-conversion-form-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const contentEl = modalEl.querySelector('.uom-conversion-form-content');

    const onSubmit = async (form) => {
        const formData = new FormData(form);

        try {
            const response = await ajax.put(form.action, formData);

            alert(response.message);

            modal.close(modalId);

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
        }
    };

    const initFormValidate = () => {
        const formEl = contentEl.querySelector('#uom-conversion-edit-form')

        $(formEl).validate({
            rules: {
                factor: {
                    required: true,
                    number: true,
                    positive: true,
                },
            },
            messages: {
                factor: {
                    required: '환산 계수를 입력해 주세요.',
                    number: '숫자만 입력해 주세요.',
                    positive: '0보다 큰 값을 입력해 주세요.'
                },
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

    const load = async (contentUrl) => {
        const response = await ajax.get(contentUrl);

        render(response);
    };

    const open = async (id) => {
        modal.setTitle(modalId, '단위 환산 수정');

        const contentUrl = `/uoms/conversions/${id}/modal/form/edit`;

        try {
            await load(contentUrl);

            modal.open(modalId);

        } catch (xhr) {
            alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
        }
    };

    return {
        open,
    };
}

export default createUomConversionEditModal;
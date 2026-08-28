import modal from "../../../common/modal.js";
import ajax from "../../../common/ajax.js";

const createUomCreateModal = () => {

    const modalId = 'uom-form-modal';
    const contentUrl = '/uoms/modal/form/create';

    const modalEl = document.querySelector(`#${modalId}`);
    const contentEl = modalEl.querySelector('.uom-form-content');

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
        const formEl = contentEl.querySelector('#uom-create-form')

        $(formEl).validate({
            rules: {
                code: {
                    required: true,
                    remote: {
                        url: '/uoms/check-code',
                        type: 'get',
                    },
                },
                name: 'required',
                scale: {
                    digits: true,
                    min: 0,
                },
            },
            messages: {
                code: {
                    required: '단위 코드를 입력해 주세요.',
                    remote: '이미 존재하는 코드입니다.'
                },
                name: '단위명을 입력해 주세요.',
                scale: {
                    digits: '소수점 자리수는 숫자만 입력 가능합니다.',
                    min: '소수점 자리수는 0 이상이어야 합니다.',
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

    const load = async () => {
        const response = await ajax.get(contentUrl);

        render(response);
    };

    const open = async () => {
        modal.setTitle(modalId, '단위 등록');

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

export default createUomCreateModal;
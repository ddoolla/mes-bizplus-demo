import modal from "../../../../common/modal.js";
import ajax from "../../../../common/ajax.js";

const createPartnerContactEditModal = () => {

    const modalId = 'contact-form-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const contentEl = modalEl.querySelector('.contact-form-content');

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
        const formEl = contentEl.querySelector('#contact-edit-form')

        $(formEl).validate({
            rules: {
                name: 'required',
                email: {
                    email: true,
                },
                phone: {
                    digits: true,
                    rangelength: [11, 11],
                },
            },
            messages: {
                name: '담당자명을 입력해 주세요.',
                email: {
                    email: '올바른 이메일 형식이 아닙니다.',
                },
                phone: {
                    digits: '숫자만 입력해 주세요.',
                    rangelength: '휴대폰 번호는 11자리로 입력해 주세요.',
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
        modal.setTitle(modalId, '담당자 수정');

        const contentUrl = `/partner-contacts/${id}/modal/edit`;

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

export default createPartnerContactEditModal;
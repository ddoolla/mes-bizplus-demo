import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'code-form-modal';

const createCodeNewFormModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const contentEl = modalEl.querySelector('.code-form-content');

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
        const formEl = contentEl.querySelector('#code-new-form')

        $(formEl).validate({
            rules: {
                code: {
                    required: true,
                    remote: {
                        url: '/common-codes/check-code',
                        type: 'get',
                        data: {
                            groupId: function () {
                                return $('[name="groupId"]').val();
                            }
                        }
                    },
                },
                name: 'required',
            },
            messages: {
                code: {
                    required: '코드를 입력해 주세요.',
                    remote: '이미 존재하는 코드 입니다.',
                },
                name: '코드명을 입력해 주세요.',
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

    const load = async (url, params) => {
        const response = await ajax.get(url, params);

        render(response);
    };

    const open = async ({title = '코드 등록', url, params}) => {
        modal.setTitle(MODAL_ID, title);

        try {
            await load(url, params);

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

export default createCodeNewFormModal;
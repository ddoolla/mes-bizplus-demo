import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'inspection-item-form-modal';

const createInspectionItemEditFormModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const contentEl = modalEl.querySelector('.inspection-item-form-content');

    const onSubmit = async (form) => {
        const formData = new FormData(form);

        try {
            const response = await ajax.put(form.action, formData);

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
        const formEl = contentEl.querySelector('#inspection-item-edit-form')

        $(formEl).validate({
            rules: {
                code: {
                    required: true,
                    remote: {
                        url: '/inspection-items/check-code',
                        type: 'get',
                        data: {
                            id: function () {
                                return $('[name="id"]').val();
                            }
                        }
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

    const load = async (contentUrl) => {
        const response = await ajax.get(contentUrl);

        render(response);
    };

    const open = async (id) => {
        modal.setTitle(MODAL_ID, '검사항목 수정');

        const contentUrl = `/inspection-items/${id}/modal/form/edit`;

        try {
            await load(contentUrl);

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

export default createInspectionItemEditFormModal;
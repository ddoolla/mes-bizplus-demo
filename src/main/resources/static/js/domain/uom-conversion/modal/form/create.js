import ajax from "../../../../common/ajax.js";
import modal from "../../../../common/modal/modal.js";
import "../../../../common/validation.js";

const createUomConversionCreateModal = () => {

    const modalId = 'uom-conversion-form-modal';
    const contentUrl = '/uoms/conversions/modal/form/create';

    const modalEl = document.querySelector(`#${modalId}`);
    const contentEl = modalEl.querySelector('.uom-conversion-form-content');

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
        const formEl = contentEl.querySelector('#uom-conversion-create-form')

        $(formEl).validate({
            rules: {
                fromUomId: {
                    required: true,
                    notEqualTo: '#uom-conversion-create-form [name="toUomId"]',
                    remote: {
                        url: '/uoms/conversions/duplicate',
                        type: 'GET',
                        data: {
                            toUomId: function () {
                                return $('#uom-conversion-create-form [name="toUomId"]').val();
                            },
                        },
                    },
                },
                toUomId: {
                    required: true,
                    notEqualTo: '#uom-conversion-create-form [name="fromUomId"]',
                    remote: {
                        url: '/uoms/conversions/duplicate',
                        type: 'get',
                        data: {
                            fromUomId: function () {
                                return $('#uom-conversion-create-form [name="fromUomId"]').val();
                            },
                        },
                    },
                },
                factor: {
                    required: true,
                    number: true,
                    positive: true,
                },
            },
            messages: {
                fromUomId: {
                    required: '변경 전 단위를 선택해 주세요.',
                    notEqualTo: '변경 후 단위와 같을 수 없습니다.',
                    remote: '이미 존재하는 단위 환산입니다.',
                },
                toUomId: {
                    required: '변경 후 단위를 선택해 주세요.',
                    notEqualTo: '변경 전 단위와 같을 수 없습니다.',
                    remote: '이미 존재하는 단위 환산입니다.',
                },
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

    const load = async () => {
        const response = await ajax.get(contentUrl);

        render(response);
    };

    const open = async () => {
        modal.setTitle(modalId, '단위 환산 등록');

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

export default createUomConversionCreateModal;
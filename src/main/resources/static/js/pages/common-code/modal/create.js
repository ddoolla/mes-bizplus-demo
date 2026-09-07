import ajax from "../../../common/ajax.js";
import modal from "../../../common/modal/modal.js";

document.addEventListener('DOMContentLoaded', function () {

    const ajaxSubmit = async (form, data) => {
        try {
            const response = await ajax.post(form.action, data);

            alert(response.message);

            modal.close('code-create-modal');

            location.reload();

        } catch (xhr) {
            alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
        }
    };

    $('#code-create-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/common-codes/check-code',
                    type: 'get',
                    data: {
                        groupId: function () {
                            return $('#group-id').val();
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
            const formData = new FormData(form);

            ajaxSubmit(form, formData);
        }
    });

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden('code-create-modal');
});
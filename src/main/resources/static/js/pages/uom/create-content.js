import modal from "../../common/modal.js";
import ajax from "../../common/ajax.js";

document.addEventListener('DOMContentLoaded', function () {

    // 등록 모달 열기
    document.querySelector('#uom-create-button').addEventListener('click', async function () {

        modal.setTitle('uom-form-modal', '단위 등록');

        try {
            const response = await ajax.get(`/uoms/new`);

            const content = document.getElementById('uom-modal-content');

            content.innerHTML = response;

            $('#uom-create-form').validate({
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
                    const formData = new FormData(form);

                    ajax.post(form.action, formData)
                        .done(function (response) {
                            alert(response.message);

                            modal.close('uom-form-modal');

                            location.reload();
                        })
                        .fail(function (xhr) {
                            alert(xhr.responseJSON.message);
                        });
                }
            });

            modal.open('uom-form-modal');

        } catch (xhr) {
            alert(xhr.responseJSON?.message);
        }
    });

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden('uom-form-modal');
});
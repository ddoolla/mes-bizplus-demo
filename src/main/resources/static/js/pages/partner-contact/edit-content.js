import modal from "../../common/modal.js";
import ajax from "../../common/ajax.js";

document.addEventListener('DOMContentLoaded', function () {

    // 수정 모달 열기
    document.querySelectorAll('.contact-edit-link')
        .forEach(link => {
            link.addEventListener('click', async (event) => {

                event.preventDefault();

                modal.setTitle('contact-form-modal', '담당자 수정');

                const id = link.dataset.id;

                try {
                    const response = await ajax.get(`/partner-contacts/${id}/edit`);

                    const content = document.getElementById('contact-modal-content');

                    content.innerHTML = response;

                    $('#contact-edit-form').validate({
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
                            const formData = new FormData(form);

                            ajax.put(form.action, formData)
                                .done(function (response) {

                                    alert(response.message);

                                    modal.close('contact-form-modal');

                                    location.reload();
                                })
                                .fail(function (xhr) {

                                    alert(xhr.responseJSON.message);
                                });
                        }
                    });

                    modal.open('contact-form-modal');

                } catch (xhr) {
                    alert(xhr.responseJSON?.message);
                }
            });
        });

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden('contact-form-modal');
});
document.addEventListener('DOMContentLoaded', function () {

    // 등록 모달 열기
    document.querySelector('#contact-create-button').addEventListener('click', async function () {

        Mes.Modal.setTitle('contact-form-modal', '담당자 등록');

        const partnerId = this.dataset.partnerId;

        const response = await Mes.Ajax.get(`/partners/${partnerId}/contacts/new`);

        const content = document.getElementById('contact-modal-content');

        content.innerHTML = response;

        $('#contact-create-form').validate({
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

                Mes.Ajax.post(form.action, formData)
                    .done(function (response) {

                        alert(response.message);

                        Mes.Modal.close('contact-form-modal');

                        location.reload();
                    })
                    .fail(function (xhr) {

                        alert(xhr.responseJSON.message);
                    });

                return false;
            }
        });

        Mes.Modal.open('contact-form-modal');
    });

    // 모달 닫기 시 폼 초기화
    Mes.Modal.resetFormOnHidden('contact-form-modal');
});
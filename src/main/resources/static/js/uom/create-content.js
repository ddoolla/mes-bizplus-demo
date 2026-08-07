document.addEventListener('DOMContentLoaded', function () {

    // 등록 모달 열기
    document.querySelector('#create-uom-button').addEventListener('click', async function () {

        Mes.Modal.setTitle('uom-form-modal', '단위 등록');

        const response = await Mes.Ajax.get(`/uoms/new`);

        const content = document.getElementById('uom-modal-content');

        content.innerHTML = response;

        $('#create-uom-form').validate({
            rules: {
                code: 'required',
                name: 'required',
                decimalPlaces: {
                    digits: true,
                    min: 0,
                    max: 6,
                },
            },
            messages: {
                code: '단위 코드를 입력해 주세요.',
                name: '단위명을 입력해 주세요.',
                decimalPlaces: {
                    digits: '소수점 자리수는 숫자만 입력 가능합니다.',
                    min: '소수점 자리수는 0 이상이어야 합니다.',
                    max: '소수점 자리수는 최대 6자리까지 입력 가능합니다.',
                },
            },
            submitHandler: function (form) {

                const formData = new FormData(form);

                Mes.Ajax.post(form.action, formData)
                    .done(function (response) {

                        alert(response.message);

                        Mes.Modal.close('uom-form-modal');

                        location.reload();
                    })
                    .fail(function (xhr) {

                        alert(xhr.responseJSON.message);
                    });

                return false;
            }
        });

        Mes.Modal.open('uom-form-modal');
    });

    // 모달 닫기 시 폼 초기화
    Mes.Modal.resetFormOnHidden('uom-form-modal');
});
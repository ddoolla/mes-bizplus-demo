import ajax from "../../../common/ajax.js";
import modal from "../../../common/modal/modal.js";

document.addEventListener('DOMContentLoaded', function () {

    // 수정 모달 열기
    document.querySelectorAll('.code-edit-link').forEach(link =>
        link.addEventListener('click', async (event) => {

                event.preventDefault();

                const link = event.currentTarget;
                const id = link.dataset.id;

                try {
                    const commonCode = await ajax.get(`/common-codes/${id}`);

                    const modalEl = document.querySelector('#code-edit-modal');
                    const formEl = modalEl.querySelector('form');

                    formEl.action = `/common-codes/${id}`;

                    formEl.querySelector('[name="id"]').value = commonCode.id;
                    formEl.querySelector('[name="code"]').value = commonCode.code;
                    formEl.querySelector('[name="name"]').value = commonCode.name;
                    formEl.querySelector('[name="description"]').value = commonCode.description;

                    $('#code-edit-form').validate({
                        rules: {
                            code: {
                                required: true,
                                remote: {
                                    url: '/common-codes/check-code',
                                    type: 'get',
                                    data: {
                                        groupId: function () {
                                            return $('#group-id').val();
                                        },
                                        id: function () {
                                            return $('#id').val();
                                        },
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

                            ajax.put(form.action, formData)
                                .done(function (response) {
                                    alert(response.message);

                                    modal.close('code-edit-modal');

                                    location.reload();
                                })
                                .fail(function (xhr) {
                                    alert(xhr.responseJSON.message);
                                });

                            return false;
                        }
                    });

                    modal.open('code-edit-modal');

                } catch (xhr) {
                    alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
                }
            }
        ));

    modal.resetFormOnHidden('code-edit-modal');
});
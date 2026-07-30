document.addEventListener('DOMContentLoaded', function () {

    // 수정 모달 열기
    document.querySelectorAll('.edit-code-link').forEach(link =>
        link.addEventListener('click', async (event) => {

                event.preventDefault();

                const link = event.currentTarget;

                const groupId = link.dataset.groupId;
                const id = link.dataset.id;

                const commonCode = await Mes.Ajax.get(`/common-codes/${id}`);

                const modal = document.querySelector('#edit-code-modal');
                const form = modal.querySelector('form');

                form.action = `/code-groups/${groupId}/codes/${id}`;

                Mes.Form.set(form, {
                    id: commonCode.id,
                    code: commonCode.code,
                    name: commonCode.name,
                    description: commonCode.description,
                });

                $('#edit-code-form').validate({
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

                        Mes.Ajax.put(form.action, formData)
                            .done(function (response) {

                                alert(response.message);

                                Mes.Modal.close('create-code-modal');

                                location.reload();
                            })
                            .fail(function (xhr) {

                                alert(xhr.responseJSON.message);
                            });

                        return false;
                    }
                });

                Mes.Modal.open('edit-code-modal');
            }
        ));

    Mes.Modal.resetFormOnHidden('edit-code-modal');
});
import createUserSingleListModal from "../../domain/user/modal/list/single.js";

document.addEventListener('DOMContentLoaded', function () {

    const userListButton = document.querySelector('#user-list-button');

    const userSingleListModal = createUserSingleListModal();

    userListButton.addEventListener('click', function () {
        userSingleListModal.open();
    });

    userSingleListModal.onSelect((user) => {
        document.querySelector('[name="userId"]').value = user.id;
        document.querySelector('[name="userName"]').value = user.name;
        userSingleListModal.close();
    });

    $('#worker-create-form').validate({
        rules: {
            userName: 'required',
            code: {
                required: true,
                remote: {
                    url: '/workers/check-code',
                    type: 'get',
                },
            },
        }, messages: {
            userName: '사용자를 선택해 주세요.',
            code: {
                required: '작업자 코드를 입력해 주세요.',
                remote: '이미 존재하는 작업자 코드입니다.',
            },
        }
    });
});
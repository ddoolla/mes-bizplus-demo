import modal from "../../../../common/modal.js";
import ajax from "../../../../common/ajax.js";
import pagination from "../../../../common/pagination.js";

const createUserSingleListModal = () => {

    const modalId = 'user-list-modal';
    const contentUrl = '/users/modal/list/single';

    const modalEl = document.querySelector(`#${modalId}`);
    const searchForm = modalEl.querySelector('.user-search-form');
    const userList = modalEl.querySelector('.user-list');

    const render = (response) => {
        userList.innerHTML = response;
    };

    const load = async (url = contentUrl, params = '') => {
        try {
            const response = await ajax.get(url, params);

            render(response);

        } catch (xhr) {
            alert(xhr.responseJSON?.message);
        }
    };

    // 모달 열기
    const open = () => {
        modal.setTitle(modalId, '사용자 목록');

        load();

        modal.open(modalId);
    };

    // 모달 닫기
    const close = () => {
        modal.close(modalId);
    };

    // 검색 폼 초기화
    $(searchForm).validate({
        submitHandler(form) {
            const params = new URLSearchParams(
                new FormData(form)
            );

            load(form.action, params.toString());
        },
    });

    // 페이지네이션 리렌더링 후 이벤트 연결
    pagination.bindEvents(userList, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(modalId);

    // 품목 선택 처리
    const onSelect = (callback) => {
        userList.addEventListener('click', function (e) {
            const button = e.target.closest('.user-select-button');

            if (!button) {
                return;
            }

            const user = {
                id: button.dataset.id,
                name: button.dataset.name,
            };

            callback(user);
        });
    };

    return {
        open,
        close,
        onSelect,
    };
};

export default createUserSingleListModal;
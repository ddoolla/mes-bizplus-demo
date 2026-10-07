import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import pagination from "../../../../common/pagination.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'user-list-modal';

const createUserSingleListModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const searchForm = modalEl.querySelector('.user-search-form');
    const userList = modalEl.querySelector('.user-list');

    let selectCallback = null;

    const render = (response) => {
        userList.innerHTML = response;
    };

    const load = async (url, params = '') => {
        try {
            const response = await ajax.get(url, params);

            render(response);

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.',
            });
        }
    };

    // 모달 열기
    const open = ({
                      title = '사용자 목록',
                      url,
                      params,
                      onSelect
                  }) => {
        selectCallback = onSelect ?? null;

        modal.setTitle(MODAL_ID, title);
        load(url, params);
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 선택 버튼 클릭 (이벤트 위임)
    userList.addEventListener('click', function (e) {
        const button = e.target.closest('.user-select-button');

        if (!button) {
            return;
        }

        const user = {
            id: button.dataset.id,
            name: button.dataset.name,
        };

        selectCallback?.(user);
    });

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
    modal.resetFormOnHidden(MODAL_ID);

    return {
        open,
        close,
    };
};

export default createUserSingleListModal;
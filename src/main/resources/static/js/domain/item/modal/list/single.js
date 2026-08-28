import modal from "../../../../common/modal.js";
import ajax from "../../../../common/ajax.js";
import pagination from "../../../../common/pagination.js";

const createItemSingleListModal = () => {

    const modalId = 'item-list-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const searchForm = modalEl.querySelector('.item-search-form');
    const itemList = modalEl.querySelector('.item-list');

    const render = (response) => {
        itemList.innerHTML = response;
    };

    const load = async (url, params = '') => {
        try {
            const response = await ajax.get(url, params);

            render(response);

        } catch (xhr) {
            alert(xhr.responseJSON?.message);
        }
    };

    // 모달 열기
    const open = (title = '품목 목록', url) => {
        modal.setTitle(modalId, title);

        load(url);

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
    pagination.bindEvents(itemList, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(modalId);

    // 품목 선택 처리
    const onSelect = (callback) => {
        itemList.addEventListener('click', function (e) {
            const button = e.target.closest('.item-select-button');

            if (!button) {
                return;
            }

            const item = {
                id: button.dataset.id,
                name: button.dataset.name,
            };

            callback(item);
        });
    };

    return {
        open,
        close,
        onSelect,
    };
};

export default createItemSingleListModal;
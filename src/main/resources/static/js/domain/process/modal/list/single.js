import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import pagination from "../../../../common/pagination.js";

const createProcessSingleListModal = () => {

    const modalId = 'process-list-modal';
    const url = '/processes/modal/list/single';

    const modalEl = document.querySelector(`#${modalId}`);
    const searchForm = modalEl.querySelector('.process-search-form');
    const itemList = modalEl.querySelector('.process-list');

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
    const open = () => {
        modal.setTitle(modalId, '공정 목록');

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
            const button = e.target.closest('.process-select-button');

            if (!button) {
                return;
            }

            const process = {
                id: button.dataset.id,
                name: button.dataset.name,
            };

            callback(process);
        });
    };

    return {
        open,
        close,
        onSelect,
    };
};

export default createProcessSingleListModal;
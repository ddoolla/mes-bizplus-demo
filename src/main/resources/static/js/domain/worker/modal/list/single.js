import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import pagination from "../../../../common/pagination.js";
import toast from "../../../../common/toast.js";

const createWorkerSingleListModal = () => {

    const modalId = 'worker-list-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const searchForm = modalEl.querySelector('.worker-search-form');
    const workerList = modalEl.querySelector('.worker-list');

    const render = (response) => {
        workerList.innerHTML = response;
    };

    const load = async ({url, params = {}}) => {
        try {
            const response = await ajax.get(url, params);

            render(response);

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON.message,
            });
        }
    };

    // 모달 열기
    const open = ({
                      title = '작업자 목록',
                      url,
                      params = {},
                      onSelect,
                  }) => {
        modal.setTitle(modalId, title);

        load({url, params});

        if (onSelect) {
            handleSelect(onSelect);
        }

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

            load({
                url: form.action,
                params: params.toString()
            });
        },
    });

    // 페이지네이션 리렌더링 후 이벤트 연결
    pagination.bindEvents(workerList, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(modalId);

    // 선택 버튼 이벤트
    const handleSelect = (onSelect) => {
        workerList.addEventListener('click', function (e) {
            const button = e.target.closest('.worker-select-button');

            if (!button) {
                return;
            }

            const worker = {
                id: button.dataset.id,
                name: button.dataset.name,
            };

            onSelect(worker);
        });
    };

    return {
        open,
        close,
    };
};

export default createWorkerSingleListModal;
import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import pagination from "../../../../common/pagination.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'worker-list-modal';

const createWorkerSingleListModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const searchForm = modalEl.querySelector('.worker-search-form');
    const workerList = modalEl.querySelector('.worker-list');

    let selectCallback = null;

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
    const open = ({title = '작업자 목록', url, params, onSelect,}) => {
        selectCallback = onSelect ?? null;

        modal.setTitle(MODAL_ID, title);
        load({url, params});
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 선택 버튼 클릭
    workerList.addEventListener('click', function (e) {
        const button = e.target.closest('.worker-select-button');

        if (!button) {
            return;
        }

        const worker = {
            id: button.dataset.id,
            name: button.dataset.name,
        };

        selectCallback?.(worker);
    });

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
    modal.resetFormOnHidden(MODAL_ID);

    return {
        open,
        close,
    };
};

export default createWorkerSingleListModal;
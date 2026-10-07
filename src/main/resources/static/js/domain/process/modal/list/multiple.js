import ajax from "../../../../common/ajax.js";
import checkbox from "../../../../common/checkbox.js";
import modal from "../../../../common/modal/modal.js";
import pagination from "../../../../common/pagination.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'process-list-modal';

const createProcessMultipleListModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const searchForm = modalEl.querySelector('.process-search-form');
    const processList = modalEl.querySelector('.process-list');

    let registerCallback = null;

    const render = (response) => {
        processList.innerHTML = response;
        checkbox.init(processList);
    };

    const load = async (url, params = {}) => {
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
    const open = ({title = '공정 목록', url, params, onRegister}) => {
        registerCallback = onRegister ?? null;

        modal.setTitle(MODAL_ID, title);
        load(url, params);
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 등록 버튼 클릭
    processList.addEventListener('click', function (e) {
        const createBtn = e.target.closest('.process-select-confirm-button');

        if (!createBtn) {
            return;
        }

        const selectedIds = checkbox.getCheckedValues(processList);

        if (!selectedIds.length) {
            toast.error({
                message: '항목을 선택해 주세요.',
            });

            return;
        }

        registerCallback?.(selectedIds);
    });

    // 검색 폼 초기화
    $(searchForm).validate({
        submitHandler: function (form) {
            const params = new URLSearchParams(
                new FormData(form)
            );

            load(form.action, params.toString());

            return false;
        }
    });

    // 페이지네이션 리렌더링 후 이벤트 연결
    pagination.bindEvents(processList, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(MODAL_ID);

    return {
        open,
        close,
    };
};

export default createProcessMultipleListModal;
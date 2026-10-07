import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import pagination from "../../../../common/pagination.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'partner-list-modal';

const createPartnerSingleListModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const searchForm = modalEl.querySelector('.partner-search-form');
    const partnerList = modalEl.querySelector('.partner-list');

    let selectCallback = null;

    const render = (response) => {
        partnerList.innerHTML = response;
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
    const open = ({title = '거래처 목록', url, params, onSelect}) => {
        selectCallback = onSelect ?? null;

        modal.setTitle(MODAL_ID, title);
        load(url, params);
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 선택 버튼 클릭
    partnerList.addEventListener('click', function (e) {
        const button = e.target.closest('.partner-select-button');

        if (!button) {
            return;
        }

        const partner = {
            id: button.dataset.id,
            name: button.dataset.name,
        };

        selectCallback?.(partner);
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
    pagination.bindEvents(partnerList, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(MODAL_ID);

    return {
        open,
        close,
    };
};

export default createPartnerSingleListModal;
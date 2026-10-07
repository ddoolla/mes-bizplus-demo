import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import checkbox from "../../../../common/checkbox.js";
import pagination from "../../../../common/pagination.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'inspection-item-list-modal';

const createInspectionItemMultipleListModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const searchForm = modalEl.querySelector('.inspection-item-search-form');
    const itemList = modalEl.querySelector('.inspection-item-list');

    let registerCallback = null;

    const render = (response) => {
        itemList.innerHTML = response;
        checkbox.init(itemList);
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
    const open = ({title = '검사항목 목록', url, params, onRegister}) => {
        registerCallback = onRegister ?? null;

        modal.setTitle(MODAL_ID, title);
        load({url, params});
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 등록 버튼 클릭 (이벤트 위임)
    itemList.addEventListener('click', function (e) {
        const createBtn = e.target.closest('.add-button');

        if (!createBtn) {
            return;
        }

        const selectedIds = checkbox.getCheckedValues(itemList);

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

            load({
                url: form.action,
                params: params.toString(),
            });

            return false;
        }
    });

    // 페이지네이션 리렌더링 후 이벤트 연결
    pagination.bindEvents(itemList, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(MODAL_ID);

    return {
        open,
        close,
    };
};

export default createInspectionItemMultipleListModal;
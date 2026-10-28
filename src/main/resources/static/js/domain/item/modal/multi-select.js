import ajax from "../../../common/ajax.js";
import toast from "../../../common/toast.js";
import modal from "../../../common/modal/modal.js";
import pagination from "../../../common/pagination.js";
import checkbox from "../../../common/checkbox.js";

const MODAL_ID = 'item-multi-select-modal';

const createItemMultiSelectModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const searchForm = modalEl.querySelector('.item-search-form');
    const listContent = modalEl.querySelector('.list-content');

    let registerCallback = null;

    const render = (response) => {
        listContent.innerHTML = response;
        checkbox.init(listContent);
    };

    const load = async ({url, params}) => {
        try {
            const response = await ajax.get(url, params);
            render(response);

            return true;

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON?.message ?? '품목 목록 조회에 실패했습니다.',
            });

            return false;
        }
    };

    // 모달 열기
    const open = async ({title, url, params, onRegister}) => {
        const success = await load({url, params});

        if (!success) {
            return;
        }

        registerCallback = onRegister ?? null;

        modal.setTitle(MODAL_ID, title);
        await load({url, params});
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 모달 등록 버튼
    listContent.addEventListener('click', function (e) {
        const button = e.target.closest('.item-register-button');

        if (!button) {
            return;
        }

        const selectedIds = checkbox.getCheckedValues(listContent);

        if (!selectedIds.length) {
            toast.error({
                message: '항목을 선택해 주세요.'
            });

            return;
        }

        registerCallback?.(selectedIds);
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
    pagination.bindEvents(listContent, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(MODAL_ID);

    return {
        open,
        close,
    };
}
export default createItemMultiSelectModal;
import ajax from "../../../common/ajax.js";
import toast from "../../../common/toast.js";
import modal from "../../../common/modal/modal.js";
import pagination from "../../../common/pagination.js";
import datepicker from "../../../common/datepicker.js";

const MODAL_ID = 'incoming-inspection-create-modal';

const createIncomingInspectionCreateModal = () => {

    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const searchForm = modalEl.querySelector('.search-form');
    const listContent = modalEl.querySelector('.list-content');

    let selectCallback = null;

    datepicker.initRange({
        formId: 'receipt-item-search-form',
        from: 'startDate',
        to: 'endDate',
    });

    const render = (response) => {
        listContent.innerHTML = response;
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
    const open = async ({title, url, params, onSelect}) => {
        const success = await load({url, params});

        if (!success) {
            return;
        }

        selectCallback = onSelect ?? null;

        modal.setTitle(MODAL_ID, title);
        await load({url, params});
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 품목 선택 이벤트
    listContent.addEventListener('click', function (e) {
        const button = e.target.closest('.select-button');

        if (!button) {
            return;
        }

        const receiptItem = {
            id: button.dataset.id,
        };

        selectCallback?.(receiptItem);
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
export default createIncomingInspectionCreateModal;
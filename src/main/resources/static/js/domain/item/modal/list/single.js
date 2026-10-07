import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import pagination from "../../../../common/pagination.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'item-list-modal';

const createItemSingleListModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const searchForm = modalEl.querySelector('.item-search-form');
    const itemList = modalEl.querySelector('.item-list');

    let selectCallback = null;

    const render = (response) => {
        itemList.innerHTML = response;
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
    const open = ({title = '품목 목록', url, params, onSelect}) => {
        selectCallback = onSelect ?? null;

        modal.setTitle(MODAL_ID, title);
        load({url, params});
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 모달 선택 버튼 클릭
    itemList.addEventListener('click', function (e) {
        const button = e.target.closest('.item-select-button');

        if (!button) {
            return;
        }

        const item = {
            id: button.dataset.id,
            name: button.dataset.name,
            defaultBom: {
                id: button.dataset.defaultBomId,
                name: button.dataset.defaultBomName
            },
            defaultRouting: {
                id: button.dataset.defaultRoutingId,
                name: button.dataset.defaultRoutingName
            }
        };

        selectCallback?.(item);
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
    pagination.bindEvents(itemList, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(MODAL_ID);

    return {
        open,
        close,
    };
};

export default createItemSingleListModal;
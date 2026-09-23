import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import pagination from "../../../../common/pagination.js";
import toast from "../../../../common/toast.js";

const createPartnerSingleListModal = () => {

    const modalId = 'partner-list-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const searchForm = modalEl.querySelector('.partner-search-form');
    const partnerList = modalEl.querySelector('.partner-list');

    const render = (response) => {
        partnerList.innerHTML = response;
    };

    const load = async (url, params = '') => {
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
    const open = ({title = '거래처 목록', url, params}) => {
        modal.setTitle(modalId, title);

        load(url, params);

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
    pagination.bindEvents(partnerList, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(modalId);

    // 품목 선택 처리
    const onSelect = (callback) => {
        partnerList.addEventListener('click', function (e) {
            const button = e.target.closest('.partner-select-button');

            if (!button) {
                return;
            }

            const partner = {
                id: button.dataset.id,
                name: button.dataset.name,
            };

            callback(partner);
        });
    };

    return {
        open,
        close,
        onSelect,
    };
};

export default createPartnerSingleListModal;
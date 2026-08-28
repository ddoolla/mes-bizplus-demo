import checkbox from "../../../../common/checkbox.js";
import ajax from "../../../../common/ajax.js";
import pagination from "../../../../common/pagination.js";
import modal from "../../../../common/modal.js";

const createBomItemMultipleListModal = () => {

    const modalId = 'bom-item-list-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const searchForm = modalEl.querySelector('.bom-item-search-form');
    const listSection = modalEl.querySelector('.bom-item-list');

    const render = (response) => {
        listSection.innerHTML = response;
        checkbox.init(listSection);
    };

    const load = async (url, params = '') => {
        try {
            const response = await ajax.get(url, params);

            render(response);

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    };

    // 모달 열기
    const open = (title = 'BOM 구성 품목 목록', url, params) => {
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
        submitHandler: function (form) {
            const params = new URLSearchParams(
                new FormData(form)
            );

            load(form.action, params.toString());

            return false;
        }
    });

    // 페이지네이션 리렌더링 후 이벤트 연결
    pagination.bindEvents(listSection, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(modalId);

    // BOM 구성품 선택 등록 처리
    const onRegister = (callback) => {
        listSection.addEventListener('click', function (e) {

            const createBtn = e.target.closest('.item-select-confirm-button');

            if (!createBtn) {
                return;
            }

            const selectedIds = checkbox.getCheckedValues(listSection);

            if (!selectedIds.length) {
                alert('등록할 품목을 선택해 주세요.')
                return;
            }

            if (!confirm('선택한 품목을 등록하시겠습니까?')) {
                return;
            }

            callback(selectedIds);
        });
    };

    return {
        open,
        close,
        onRegister,
    };
};

export default createBomItemMultipleListModal;
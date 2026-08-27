import ajax from "../../../common/ajax.js";
import checkbox from "../../../common/checkbox.js";
import modal from "../../../common/modal.js";
import pagination from "../../../common/pagination.js";

const createProcessMultiSelectModal = () => {
    const modalId = 'process-multi-select-modal';
    const listUrl = '/processes/modal/multi-select-list';

    const modalEl = document.querySelector(`#${modalId}`);
    const searchForm = modalEl.querySelector('.process-search-form');
    const processList = modalEl.querySelector('.process-list');

    const render = (response) => {
        processList.innerHTML = response;
        checkbox.init(processList);
    };

    const load = async (url = listUrl, params = '') => {
        try {
            const response = await ajax.get(url, params);
            render(response);

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    };

    // 모달 열기
    const open = (title = '공정 목록') => {
        modal.setTitle(modalId, title);

        load();

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
    pagination.bindEvents(processList, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(modalId);

    // 등록
    const onRegister = (callback) => {
        processList.addEventListener('click', function (e) {

            const createBtn = e.target.closest('.process-select-confirm-button');

            if (!createBtn) {
                return;
            }

            const selectedIds = checkbox.getCheckedValues(processList);

            if (!selectedIds.length) {
                alert('공정을 선택해 주세요.')
                return;
            }

            if (!confirm('선택한 공정을 등록하시겠습니까?')) {
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

export default createProcessMultiSelectModal;
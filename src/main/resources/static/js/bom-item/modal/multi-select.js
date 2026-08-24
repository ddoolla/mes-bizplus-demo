const createBomItemMultiSelectModal = () => {

    const modalId = 'bom-item-multi-select-modal';
    const listUrl = '/items/modal/multi-select-list';

    const modal = document.querySelector(`#${modalId}`);
    const searchForm = modal.querySelector('.bom-item-search-form');
    const listSection = modal.querySelector('.bom-item-list');

    const render = (response) => {
        listSection.innerHTML = response;
        Mes.Checkbox.init(listSection);
    };

    const load = (url, params = '') => {
        Mes.Ajax.get(url, params)
            .done(render)
            .fail(function (xhr) {
                alert(xhr.responseJSON.message);

                Mes.Modal.close(modalId);
            });
    };

    // 모달 열기
    const open = (title = 'BOM 구성 품목 목록', url, params) => {
        Mes.Modal.setTitle(modalId, title);

        load(url, params);

        Mes.Modal.open(modalId);
    };

    // 모달 닫기
    const close = () => {
        Mes.Modal.close(modalId);
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
    Mes.Pagination.bindEvents(listSection, render);

    // 모달 닫기 시 폼 초기화
    Mes.Modal.resetFormOnHidden(modalId);

    // BOM 구성품 선택 등록 처리
    const onRegister = (callback) => {
        listSection.addEventListener('click', function (e) {

            const createBtn = e.target.closest('.item-select-confirm-button');

            if (!createBtn) {
                return;
            }

            const selectedIds = Mes.Checkbox.getCheckedValues(listSection);

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
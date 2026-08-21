const createProcessMultiSelectModal = () => {

    const modalId = 'process-multi-select-modal';
    const listUrl = '/processes/modal/multi-select-list';

    const modal = document.querySelector(`#${modalId}`);
    const searchForm = modal.querySelector('.process-search-form');
    const processList = modal.querySelector('.process-list');

    const render = (response) => {
        processList.innerHTML = response;
        Mes.Checkbox.init(processList);
    };

    const load = (url = listUrl, params = '') => {
        Mes.Ajax.get(url, params)
            .done(render)
            .fail(function (xhr) {
                alert(xhr.responseJSON.message);

                Mes.Modal.close(modalId);
            });
    };

    // 모달 열기
    const open = (title = '공정 목록') => {
        Mes.Modal.setTitle(modalId, title);

        load();

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
    Mes.Pagination.bindEvents(processList, render);

    // 모달 닫기 시 폼 초기화
    Mes.Modal.resetFormOnHidden(modalId);

    // 등록
    const onRegister = (callback) => {
        processList.addEventListener('click', function (e) {

            const createBtn = e.target.closest('.process-select-confirm-button');

            if (!createBtn) {
                return;
            }

            const selectedIds = Mes.Checkbox.getCheckedValues(processList);

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
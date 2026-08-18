const createItemMultiSelectModal = () => {

    const modalId = 'item-multi-select-modal';
    const listUrl = '/items/modal/multi-select-list';

    const modal = document.querySelector(`#${modalId}`);
    const searchForm = modal.querySelector('.item-search-form');
    const itemList = modal.querySelector('.item-list');

    const render = (response) => {
        itemList.innerHTML = response;
        Mes.Checkbox.init(itemList);
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
    const open = (title = '품목 목록') => {
        Mes.Modal.setTitle(modalId, title);

        load();

        Mes.Modal.open(modalId);
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
    Mes.Pagination.bindEvents(itemList, render);

    // 모달 닫기 시 폼 초기화
    Mes.Modal.resetFormOnHidden(modalId);

    // 품목 등록 처리
    const onRegister = (callback) => {
        itemList.addEventListener('click', function (e) {

            const createBtn = e.target.closest('.item-select-confirm-button');

            if (!createBtn) {
                return;
            }

            const selectedIds = Mes.Checkbox.getCheckedValues(itemList);

            if (!selectedIds.length) {
                alert('BOM에 등록할 구성 품목을 선택해 주세요.')
                return;
            }

            if (!confirm('선택한 품목을 BOM 구성 품목으로 등록하시겠습니까?')) {
                return;
            }

            callback(selectedIds);
        });
    };

    return {
        open,
        onRegister,
    };
};
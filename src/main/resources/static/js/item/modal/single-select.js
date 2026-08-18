const createItemSingleSelectModal = () => {

    const modalId = 'item-single-select-modal';
    const listUrl = '/items/modal/single-select-list';

    const modal = document.querySelector(`#${modalId}`);
    const searchForm = modal.querySelector('.item-search-form');
    const itemList = modal.querySelector('.item-list');

    const render = (response) => {
        itemList.innerHTML = response;
    };

    const load = (url = listUrl, params = '') => {
        Mes.Ajax.get(url, params)
            .done(render)
            .fail(function (xhr) {
                alert(xhr.responseJSON?.message);

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
        submitHandler(form) {
            const params = new URLSearchParams(
                new FormData(form)
            );

            load(form.action, params.toString());
        },
    });

    // 페이지네이션 리렌더링 후 이벤트 연결
    Mes.Pagination.bindEvents(itemList, render);

    // 모달 닫기 시 폼 초기화
    Mes.Modal.resetFormOnHidden(modalId);

    // 품목 선택 처리
    const onSelect = (callback) => {
        itemList.addEventListener('click', function (e) {

            const button = e.target.closest('.item-select-button');

            if (!button) {
                return;
            }

            const item = {
                id: button.dataset.id,
                name: button.dataset.name,
            };

            callback(item);

            Mes.Modal.close(modalId);
        });
    };

    return {
        open,
        onSelect,
    };
};
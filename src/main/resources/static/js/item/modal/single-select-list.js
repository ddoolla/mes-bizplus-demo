document.addEventListener('DOMContentLoaded', function () {

    const bomCreateForm = document.querySelector('#bom-create-form');
    const modalOpenButton = document.querySelector('#item-list-button');
    const itemListModal = document.querySelector('#item-list-modal');
    const itemList = itemListModal.querySelector('#item-list');

    // 모달 열기
    modalOpenButton.addEventListener('click', async function(){

        Mes.Modal.setTitle('item-list-modal', '제품 목록');

        itemList.innerHTML = await Mes.Ajax.get(`/items/modal/single-select-list`);

        // 검색 폼 validation
        $('#item-search-form').validate({
            submitHandler: function (form) {
                const formData = new FormData(form);
                const params = new URLSearchParams(formData);

                Mes.Ajax.get(form.action, params.toString())
                    .done(function (response) {
                        itemList.innerHTML = response;
                    })
                    .fail(function (xhr) {
                        alert(xhr.responseJSON.message);
                        Mes.Modal.close('item-list-modal');
                    });

                return false;
            }
        });

        Mes.Modal.open('item-list-modal');
    });

    // 모달 닫기 시 폼 초기화
    Mes.Modal.resetFormOnHidden('item-list-modal');

    // 페이지네이션 비동기 처리
    Mes.Pagination.bindAjax(itemList);

    // 제품 선택 처리
    itemList.addEventListener('click', function (e) {
        const button = e.target.closest('.item-select-button');

        if (!button) {
            return;
        }

        const itemId = button.dataset.id;
        const itemName = button.dataset.name;

        const itemIdEl = bomCreateForm.querySelector('[name="itemId"]');
        const itemNameEl = bomCreateForm.querySelector('[name="itemName"]');

        itemIdEl.value = itemId;
        itemNameEl.value = itemName;

        Mes.Modal.close('item-list-modal');
    });
});
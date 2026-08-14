document.addEventListener('DOMContentLoaded', function () {

    const modalOpenButton = document.querySelector('#bom-item-create-button');
    const itemListModal = document.querySelector('#item-list-modal');
    const itemList = itemListModal.querySelector('#item-list');

    const renderItemList = (response) => {
        itemList.innerHTML = response;
        Mes.Checkbox.init(itemList);
    };

    // 모달 열기
    modalOpenButton.addEventListener('click', async function(){

        Mes.Modal.setTitle('item-list-modal', 'BOM 구성품 목록');

        const response = await Mes.Ajax.get(`/items/modal/multi-select-list`);
        renderItemList(response);

        // 검색 폼 validation
        $('#item-search-form').validate({
            submitHandler: function (form) {
                const formData = new FormData(form);
                const params = new URLSearchParams(formData);

                Mes.Ajax.get(form.action, params.toString())
                    .done(function (response) {
                        renderItemList(response);
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
    Mes.Pagination.bindAjax(itemList, renderItemList);
});
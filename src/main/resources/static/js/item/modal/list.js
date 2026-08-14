document.addEventListener('DOMContentLoaded', function () {

    // todo 싱글 멀티에서 공통으로 뺄 수 있는게 있는지 ?
    const itemListModal = document.querySelector('#item-list-modal');
    const itemList = itemListModal.querySelector('#item-list');

    const renderItemList = (response) => {
        itemList.innerHTML = response;
        Mes.Checkbox.init(itemList);
    };

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

    // 페이지네이션 비동기 처리
    Mes.Pagination.bindAjax(itemList, renderItemList);

    // 모달 닫기 시 폼 초기화
    Mes.Modal.resetFormOnHidden('item-list-modal');
});
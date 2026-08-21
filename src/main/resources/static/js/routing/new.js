document.addEventListener('DOMContentLoaded', function () {

    const routingCreateForm = document.querySelector('#routing-create-form');
    const modalOpenButton = document.querySelector('#item-list-button');

    const itemSingleSelectModal = createItemSingleSelectModal();

    // 품목 선택 모달 열기
    modalOpenButton.addEventListener('click', function () {
        itemSingleSelectModal.open(
            '제품 목록 (완제품/반제품)',
            '/items/modal/product/single-select-list'
        );
    });

    // 품목 선택 처리
    itemSingleSelectModal.onSelect(function (item) {
        routingCreateForm.querySelector('[name="itemId"]').value = item.id;
        routingCreateForm.querySelector('[name="itemName"]').value = item.name;
        itemSingleSelectModal.close();
    });

    $('#routing-create-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/routings/check-code',
                    type: 'get',
                },
            },
            name: 'required',
            itemName: 'required',
        }, messages: {
            code: {
                required: '제품 공정 코드를 입력해 주세요.',
                remote: '이미 존재하는 제품 공정 코드입니다.',
            },
            name: '제품 공정명을 입력해 주세요.',
            itemName: '제품을 선택해 주세요.'
        }, errorPlacement: function (error, element) {
            if (element.attr('name') === 'itemName') {
                error.appendTo('#item-error-container');
            } else {
                error.insertAfter(element);
            }
        },
    });
});
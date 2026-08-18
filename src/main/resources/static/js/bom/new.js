document.addEventListener('DOMContentLoaded', function () {

    const itemSingleSelectModal = createItemSingleSelectModal();

    const bomCreateForm = document.querySelector('#bom-create-form');
    const modalOpenButton = document.querySelector('#item-list-button');

    // 품목 선택 모달 열기
    modalOpenButton.addEventListener('click', function () {
        itemSingleSelectModal.open('제품 목록');
    });

    // 품목 선택 처리
    itemSingleSelectModal.onSelect(function (item) {
        bomCreateForm.querySelector('[name="itemId"]').value = item.id;
        bomCreateForm.querySelector('[name="itemName"]').value = item.name;
    });

    // BOM 등록 폼 유효성검사
    $('#bom-create-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/boms/check-code',
                    type: 'get',
                },
            },
            name: 'required',
            itemName: 'required',
        }, messages: {
            code: {
                required: 'BOM 코드를 입력해 주세요.',
                remote: '이미 존재하는 BOM 코드입니다.',
            },
            name: 'BOM명을 입력해 주세요.',
            itemName: '품목을 선택해 주세요.'
        }
    });
});
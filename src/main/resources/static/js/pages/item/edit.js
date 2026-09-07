import ajax from "../../common/ajax.js";

document.addEventListener('DOMContentLoaded', function () {

    const deleteButtons = document.querySelectorAll('.item-file-delete-button');

    deleteButtons.forEach(button => {
        button.addEventListener('click', async function (e) {

            if (!confirm('이미지를 삭제하시겠습니까? 삭제 시 복구할 수 없습니다.')) {
                return;
            }

            const {id, fileId} = e.currentTarget.dataset;

            try {
                const response = await ajax.delete('/item-files', {id, fileId});

                alert(response.message);

                location.reload();

            } catch (xhr) {
                alert(xhr.responseJSON?.message || '처리 중 오류가 발생하였습니다.');
            }
        });
    });


    $('#item-edit-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/items/check-code',
                    type: 'get',
                    data: {
                        id: function () {
                            return $('[name="id"]').val();
                        }
                    }
                },
            },
            name: 'required',
            type: 'required',
            uomId: 'required',
            lotManaged: {
                remote: {
                    url: '/items/check-lot-managed',
                    type: 'get',
                    data: {
                        id: function () {
                            return $('[name="id"]').val();
                        },
                    },
                },
            },
        }, messages: {
            code: {
                required: '품목 코드를 입력해 주세요.',
                remote: '이미 존재하는 품목 코드입니다.',
            },
            name: '품목명을 입력해 주세요.',
            type: '품목 유형을 선택해 주세요.',
            uomId: '단위를 선택해 주세요.',
            lotManaged: {
                remote: '재고가 있는 품목은 LOT 관리 여부를 변경할 수 없습니다.'
            }
        },
        errorPlacement: function (error, element) {
            if (element.attr('name') === 'lotManaged') {
                error.appendTo('#lotManaged-error-container');
            } else {
                error.insertAfter(element);
            }
        },
    });

    $('input[name="lotManaged"]').on('change', function () {
        $('input[name="lotManaged"]').first().valid();
    });
});
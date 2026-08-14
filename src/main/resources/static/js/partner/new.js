document.addEventListener('DOMContentLoaded', function () {

    $('#address-search-button').on('click', function () {
       Mes.Address.search('partner-create-form');
    });

    $('#partner-create-form').validate({
        rules: {
            code: {
                required: true,
                remote: {
                    url: '/partners/check-code',
                    type: 'get',
                },
            },
            name: 'required',
            businessNo: {
                digits: true,
                rangelength: [10, 10],
            },
            corporateNo: {
                digits: true,
                rangelength: [13, 13],
            },
            email: {
                email: true,
            },
        }, messages: {
            code: {
                required: '거래처 코드를 입력해 주세요.',
                remote: '이미 존재하는 거래처 코드입니다.',
            },
            name: '거래처명을 입력해 주세요.',
            businessNo: {
                digits: '숫자만 입력해 주세요.',
                rangelength: '사업자등록번호는 10자리입니다.',
            },
            corporateNo: {
                digits: '숫자만 입력해 주세요.',
                rangelength: '법인자등록번호는 10자리입니다.',
            },
            email: {
                email: '올바른 이메일 형식이 아닙니다.',
            },
        }
    });
});
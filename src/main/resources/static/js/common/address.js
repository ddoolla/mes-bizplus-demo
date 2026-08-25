/*
* 개별 페이지에서 다음 우편번호 API 스크립트 추가
* <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
* */
const address = {
    search(formId) {
        new daum.Postcode({
            oncomplete: function (data) {

                const address = data.userSelectedType === 'R'
                    ? data.roadAddress
                    : data.jibunAddress;

                const $form = $('#' + formId);

                $form.find('[name="zipCode"]').val(data.zonecode);
                $form.find('[name="address"]').val(address);
                $form.find('[name="addressDetail"]').focus();
            }
        }).open();
    }
};

export default address;
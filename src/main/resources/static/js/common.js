function settingMonthPicker(tagName) {
    let nowDate = new Date();
    let year = nowDate.getFullYear();
    options = {
        pattern: 'yyyy-mm',
        selectedYear: year,
        startYear: year - 5,
        finalYear: year + 5,
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
    };
    $('input[name="' + tagName + '"]').monthpicker(options);
}

/**
 * 다음 주소 찾기
 * @param arr
 * @param form
 * @param flag
 */
function getAddressSearch(arr, form, flag) {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if (data.userSelectedType === 'R') {
                //법정동명이 있을 경우 추가한다.
                if (data.bname !== '') {
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if (data.buildingName !== '') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
            }

            var $target_form = $("form[name='" + form + "']");
            $target_form.find("input[name='" + arr[0] + "']").val(data.zonecode);
            $target_form.find("input[name='" + arr[1] + "']").val(fullAddr);
            $target_form.find("input[name='" + arr[2] + "']").focus();

            if (typeof (flag) != 'undefined') {
                if (flag) {
                    // 주소로 상세 정보를 검색
                    geocoder.addressSearch(data.address, function (results, status) {
                        // 정상적으로 검색이 완료됐으면
                        if (status === daum.maps.services.Status.OK) {

                            var result = results[0]; //첫번째 결과의 값을 활용

                            $target_form.find('input[name="x"]').val(result.x);
                            $target_form.find('input[name="y"]').val(result.y);

                            // 해당 주소에 대한 좌표를 받아서
                            var coords = new daum.maps.LatLng(result.y, result.x);

                            // 지도를 보여준다.
                            mapContainer.style.display = "block";
                            map.relayout();

                            // 지도 중심을 변경한다.
                            map.setCenter(coords);

                            // 마커를 결과값으로 받은 위치로 옮긴다.
                            marker.setPosition(coords)
                        }
                    });
                }
            }
        }
    }).open();
}
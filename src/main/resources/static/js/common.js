$(document).ready(function () {
    $('.alert .close').on('click', function (e) {
        var self = $(this)
        self.closest('div').slideUp(200, function (e) {
            self.closest('div').remove()
        });
    });
    if (typeof ($.datepicker) != 'undefined') {
        $.datepicker.setDefaults({
            dateFormat: 'yy-mm-dd',
            prevText: '이전 달',
            nextText: '다음 달',
            monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
            monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
            dayNames: ['일', '월', '화', '수', '목', '금', '토'],
            dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
            dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
            showMonthAfterYear: true,
            yearSuffix: '년'
        });
    }
});

function settingDatePicker(arr, form) {
    if (arr.length > 0) {
        let $form = $('form[name="' + form + '"]');
        if (arr.length == 1) {
            $form.find('input[name="' + arr[0] + '"]').datepicker();
        } else if (arr.length == 2) {
            $form.find('input[name="' + arr[0] + '"]').datepicker();
            $form.find('input[name="' + arr[0] + '"]').datepicker('option', 'maxDate', $form.find('input[name="' + arr[1] + '"]').val());
            $form.find('input[name="' + arr[0] + '"]').datepicker('option', 'onClose', function (selected) {
                $form.find('input[name="' + arr[1] + '"]').datepicker('option', 'minDate', selected);
            });
            $form.find('input[name="' + arr[1] + '"]').datepicker();
            $form.find('input[name="' + arr[1] + '"]').datepicker('option', 'minDate', $form.find('input[name="' + arr[0] + '"]').val());
            $form.find('input[name="' + arr[1] + '"]').datepicker('option', 'onClose', function (selected) {
                $form.find('input[name="' + arr[0] + '"]').datepicker("option", "maxDate", selected);
            });
        }
    }
}

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

function option(width, height) {
    if (width == '') width = 700
    if (height == '') height = 660

    var left = (screen.availWidth - width) / 2
    var top = (screen.availHeight - height) / 2
    var specs = 'width=' + width
    specs += ',height=' + height
    specs += ',left=' + left
    specs += ',top=' + top

    return specs
}

/**
 * 랜덤 코드 (접두사 + 랜덤코드 + 시간)
 * @param prefix
 * @param len
 * @returns {string}
 */
function makeRandomCode(prefix, len) {
    let code = '';
    const characters = '123456789';
    const charactersLength = characters.length;
    for (var i = 0; i < len; i++) {
        code += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return prefix + code;
}

function makeOrderCode(prefix, orderDate, len) {
    let code = '';
    const characters = '123456789';
    const charactersLength = characters.length;
    for (var i = 0; i < len; i++) {
        code += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return prefix + orderDate + "_" + code;
}

function accountListPopup(value) {
    window.open(
        '/account/popupList?value=' + value,
        'popup_accountList',
        option(1100, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function materialListPopup(value) {
    window.open(
        '/material/popupList?value=' + value,
        'popup_materialList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function typeMaterialListPopup(value, type) {
    window.open(
        '/popup/materialList.do?value=' + value + '&type=' + type,
        'popup_materialList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function materialPurchaseListPopup(value) {
    window.open(
        '/popup/materialPurchaseList.do?value=' + value,
        'popup_materialPurchaseList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function importInspectionMaterial(value) {
    window.open(
        '/popup/importInspectionMaterialList.do?value=' + value,
        'popup_importInspectionMaterialList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function moldListPopup(value) {
    window.open(
        '/popup/moldList.do?value=' + value,
        'popup_moldList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function materialReleaseListPopup(value) {
    window.open(
        '/popup/materialReleaseList.do?value=' + value,
        'popup_materialReleaseList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function subMaterialListPopup(value) {
    window.open(
        '/popup/subMaterialList.do?value=' + value,
        'popup_subMaterialList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function materialStockListPopup(value) {
    window.open(
        '/popup/materialStockList.do?value=' + value,
        'popup_materialStockList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function productStockListPopup(value) {
    window.open(
        '/popup/productStockList.do?value=' + value,
        'popup_productStockList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}



function productOrderListPopup(value) {
    window.open(
        '/popup/productOrderList.do?value=' + value,
        'popup_productOrderList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function productListPopup(value) {
    window.open(
        '/product/popupList?value=' + value,
        'popup_productList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function inspectionCodeListPopup(value) {
    window.open(
        '/inspectionCode/popupList?value=' + value,
        'popup_inspectionCodeList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function inspectionContentListPopup(value) {
    window.open(
        '/inspectionContent/popupList?value=' + value,
        'popup_inspectionContentList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function orderListPopup(value) {
    window.open(
        '/order/popupList?value=' + value,
        'popup_orderList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function productionListPopup(value) {
    window.open(
        '/production/popupList?value=' + value,
        'popup_productionList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function materialInboundListPopup(value) {
    window.open(
        '/materialInbound/popupList?value=' + value,
        'popup_popupInboundList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function processCompleteProductionListPopup(value) {
    window.open(
        '/production/popupProcessCompletedList?value=' + value,
        'popup_productionList',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function processInspectionReportPopup(processInspectionId) {
    window.open(
        '/processInspectionReport/info?id=' + processInspectionId,
        'popup_processInspectionReport',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}

function shipmentInspectionReportPopup(shipmentInspectionId) {
    window.open(
        '/shipmentInspectionReport/info?id=' + shipmentInspectionId,
        'popup_shipmentInspectionReport',
        option(1400, 700) + ', toolbar=no,menubar=no,status=no,scrollbar=no,resizable=no'
    );
}
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
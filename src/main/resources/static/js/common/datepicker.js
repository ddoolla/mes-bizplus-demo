window.Mes = window.Mes || {};

/* jquery ui - datepicker 한국어 세팅 */
$(function () {
    if ($.datepicker) {
        $.datepicker.setDefaults({
            dateFormat: "yy-mm-dd",
            prevText: "이전 달",
            nextText: "다음 달",
            monthNames: [
                "1월","2월","3월","4월",
                "5월","6월","7월","8월",
                "9월","10월","11월","12월"
            ],
            dayNamesMin: [
                "일","월","화","수","목","금","토"
            ],
            showMonthAfterYear: true,
            yearSuffix: "년"
        });
    }
});

Mes.Datepicker = {

    init(from, to) {

        const $from = $('#' + from);
        const $to = $('#' + to);

        $from.datepicker({
            dateFormat: 'yy-mm-dd',
            onSelect: function (date) {
                $to.datepicker('option', 'minDate', date);
            }
        });

        $to.datepicker({
            dateFormat: 'yy-mm-dd',
            onSelect: function (date) {
                $from.datepicker('option', 'maxDate', date);
            }
        });
    },
}
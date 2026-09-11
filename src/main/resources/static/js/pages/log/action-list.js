import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    datepicker.init({
        formId: 'action-log-search-form',
        from: 'startDate',
        to: 'endDate',
    });

});
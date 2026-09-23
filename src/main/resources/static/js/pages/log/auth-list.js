import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    datepicker.initRange({
        formId: 'auth-log-search-form',
        from: 'startDate',
        to: 'endDate',
    });
});
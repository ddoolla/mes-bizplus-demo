import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    datepicker.initRange({
        formId: 'production-result-search-form',
        from: 'startDate',
        to: 'endDate'
    });
});
import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    datepicker.initRange({
        formId: 'search-form',
        from: 'startDate',
        to: 'endDate'
    });
});
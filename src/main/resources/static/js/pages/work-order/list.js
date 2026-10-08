import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    datepicker.initRange({
        formId: 'work-order-search-form',
        from: 'startDate',
        to: 'endDate'
    });
});
import datepicker from "../../common/datepicker.js";

document.addEventListener('DOMContentLoaded', function () {

    datepicker.initRange({
        formId: 'inventory-transaction-search-form',
        from: 'startDate',
        to: 'endDate',
    });
});
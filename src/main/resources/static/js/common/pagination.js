window.Mes = window.Mes || {};

Mes.Pagination = {
    bindAjax(container, onSuccess) {
        container.addEventListener('click', function (e) {
            const link = e.target.closest('.app-pagination a');

            if (!link) {
                return;
            }

            e.preventDefault();

            Mes.Ajax.get(link.href)
                .done(function (response) {
                    onSuccess(response);
                })
                .fail(function (xhr) {
                    alert(xhr.responseJSON.message);
                });
        });
    },
};
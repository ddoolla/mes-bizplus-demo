window.Mes = window.Mes || {};

Mes.Pagination = {
    bindEvents(container, onLoad) {
        container.addEventListener('click', function (e) {
            const link = e.target.closest('.app-pagination a');

            if (!link || !container.contains(link)) {
                return;
            }

            e.preventDefault();

            Mes.Ajax.get(link.href)
                .done(onLoad)
                .fail(function (xhr) {
                    alert(xhr.responseJSON.message);
                });
        });
    },
};
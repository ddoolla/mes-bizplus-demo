window.Mes = window.Mes || {};

Mes.Modal = {

    open(id) {

        const element = document.getElementById(id);

        bootstrap.Modal
            .getOrCreateInstance(element)
            .show();
    },

    close(id) {

        const element = document.getElementById(id);

        bootstrap.Modal
            .getOrCreateInstance(element)
            .hide();
    }
};
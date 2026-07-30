window.Mes = window.Mes || {};

Mes.Modal = {

    open(id, data = {}) {

        const modal = document.getElementById(id);

        Object.assign(modal.dataset, data);

        bootstrap.Modal
            .getOrCreateInstance(modal)
            .show();
    },

    close(id) {

        const modal = document.getElementById(id);

        bootstrap.Modal
            .getOrCreateInstance(modal)
            .hide();
    },

    setTitle(id, title) {

        const modal = document.getElementById(id);

        const titleElement = modal.querySelector('.modal-title');

        if (!titleElement) {
            return;
        }

        titleElement.textContent = title;
    },

    /*
    * 수정 모달을 열 때 open, onShow 로직을 나누어 봤는데, UX가 별로인 듯
    * 모달이 먼저 출력되고나서 데이터가 들어가는게 눈에 보임.
    * */
    onShow(id, callback) {
        const modal = document.getElementById(id);

        modal.addEventListener('shown.bs.modal', () => {
            callback?.(modal);
        });
    },

    onHidden(id, callback) {
        const modal = document.getElementById(id);

        modal.addEventListener('hidden.bs.modal', () => {
            callback?.(modal);
        });
    },

    resetFormOnHidden(id) {
        this.onHidden(id, (modal) => {

            const form = modal.querySelector('form');

            if (!form) {
                return;
            }

            form.reset();

            const validator = $(form).data('validator');

            if (validator) {
                validator.resetForm();
            }
        })
    }
};
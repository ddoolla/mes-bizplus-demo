const modal = {
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
        });
    }
};

export default modal;
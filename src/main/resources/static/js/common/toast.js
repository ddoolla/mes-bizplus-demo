const TOAST_STORAGE_KEY = 'client-toast';

const toast = {
    show(message, type = 'success', delay = 2000) {
        const toastEl = document.querySelector('#client-toast');

        if (!toastEl) {
            return;
        }

        const iconEl = toastEl.querySelector('.toast-header i');
        const messageEl = toastEl.querySelector('.toast-body');

        messageEl.textContent = message;
        iconEl.className = 'bi bi-square-fill';

        switch (type) {
            case 'success':
                iconEl.classList.add('text-success');
                break;

            case 'error':
                iconEl.classList.add('text-danger');
                break;

            case 'warning':
                iconEl.classList.add('text-warning');
                break;

            case 'info':
                iconEl.classList.add('text-info');
                break;

            default:
                iconEl.classList.add('text-success');
                break;
        }

        const toastInstance = bootstrap.Toast.getOrCreateInstance(toastEl, {
            autohide: true,
            delay,
        });

        toastInstance.show();
    },

    success({ message, delay }) {
        this.show(message, 'success', delay);
    },

    error({ message, delay }) {
        this.show(message, 'error', delay);
    },

    warning({ message, delay }) {
        this.show(message, 'warning', delay);
    },

    info({ message, delay }) {
        this.show(message, 'info', delay);
    },

    /*
    * 페이지 새로고침 이후 출력할 토스트 세션 저장소 저장
    * */
    afterReload({ message, type = 'success', delay = 2000 }) {
        sessionStorage.setItem(
            TOAST_STORAGE_KEY,
            JSON.stringify({
                message,
                type,
                delay,
            })
        );
    },

    /*
    * 페이지 새로고침 후 세션 저장소에 저장된 토스트 처리
    * */
    init() {
        const toastData = sessionStorage.getItem(TOAST_STORAGE_KEY);

        if (!toastData) {
            return;
        }

        sessionStorage.removeItem(TOAST_STORAGE_KEY);

        try {
            const {
                message,
                type = 'success',
                delay = 2000,
            } = JSON.parse(toastData);

            this.show(message, type, delay);

        } catch (error) {
            console.error('토스트 데이터를 처리할 수 없습니다.', error);
        }
    },
};

export default toast;
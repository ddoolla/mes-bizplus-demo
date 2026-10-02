import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import toast from "../../../../common/toast.js";

const createRoutingSingleListModal = () => {

    const modalId = 'routing-list-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const routingList = modalEl.querySelector('.routing-list');

    const render = (response) => {
        routingList.innerHTML = response;
    };

    const load = async ({url, params = {}}) => {
        try {
            const response = await ajax.get(url, params);

            render(response);

        } catch (xhr) {
            toast.error({
                message: xhr.responseJSON.message,
            });
        }
    };

    // 모달 열기
    const open = ({
                      title = '제품공정 목록',
                      url,
                      params = {}
                  }) => {
        modal.setTitle(modalId, title);

        load({url, params});

        modal.open(modalId);
    };

    // 모달 닫기
    const close = () => {
        modal.close(modalId);
    };

    // BOm 선택 처리
    const onSelect = (callback) => {
        routingList.addEventListener('click', function (e) {
            const button = e.target.closest('.routing-select-button');

            if (!button) {
                return;
            }

            const routing = {
                id: button.dataset.id,
                name: button.dataset.name,
            };

            callback(routing);
        });
    };

    return {
        open,
        close,
        onSelect,
    };
};

export default createRoutingSingleListModal;
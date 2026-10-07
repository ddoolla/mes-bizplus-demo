import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import toast from "../../../../common/toast.js";

const MODAL_ID = 'bom-list-modal';

const createBomSingleListModal = () => {
    const modalEl = document.querySelector(`#${MODAL_ID}`);
    const bomList = modalEl.querySelector('.bom-list');

    let selectCallback = null;

    const render = (response) => {
        bomList.innerHTML = response;
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
                      title = 'BOM 목록',
                      url,
                      params = {},
                      onSelect,
                  }) => {
        selectCallback = onSelect ?? null;

        modal.setTitle(MODAL_ID, title);
        load({url, params});
        modal.open(MODAL_ID);
    };

    // 모달 닫기
    const close = () => {
        modal.close(MODAL_ID);
    };

    // 선택 버튼 클릭 (이벤트 위임)
    bomList.addEventListener('click', function (e) {
        const button = e.target.closest('.bom-select-button');

        if (!button) {
            return;
        }

        const bom = {
            id: button.dataset.id,
            name: button.dataset.name,
        };

        selectCallback?.(bom);
    });

    return {
        open,
        close,
    };
};

export default createBomSingleListModal;
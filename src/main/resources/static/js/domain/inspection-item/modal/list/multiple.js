import modal from "../../../../common/modal/modal.js";
import ajax from "../../../../common/ajax.js";
import checkbox from "../../../../common/checkbox.js";
import pagination from "../../../../common/pagination.js";

const createInspectionItemMultipleListModal = () => {

    const modalId = 'inspection-item-list-modal';

    const modalEl = document.querySelector(`#${modalId}`);
    const searchForm = modalEl.querySelector('.inspection-item-search-form');
    const itemList = modalEl.querySelector('.inspection-item-list');

    const render = (response) => {
        itemList.innerHTML = response;
        checkbox.init(itemList);
    };

    const load = async ({url, params = {}}) => {

        try {
            const response = await ajax.get(url, params);
            render(response);

        } catch (xhr) {
            alert(xhr.responseJSON.message);
        }
    };

    // 모달 열기
    const open = ({
                      title = '검사항목 목록',
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

    // 검색 폼 초기화
    $(searchForm).validate({
        submitHandler: function (form) {
            const params = new URLSearchParams(
                new FormData(form)
            );

            load({
                url: form.action,
                params: params.toString(),
            });

            return false;
        }
    });

    // 페이지네이션 리렌더링 후 이벤트 연결
    pagination.bindEvents(itemList, render);

    // 모달 닫기 시 폼 초기화
    modal.resetFormOnHidden(modalId);

    // 품목 등록 처리
    const onRegister = (callback) => {
        itemList.addEventListener('click', function (e) {

            const createBtn = e.target.closest('.add-button');

            if (!createBtn) {
                return;
            }

            const selectedIds = checkbox.getCheckedValues(itemList);

            if (!selectedIds.length) {
                alert('등록할 항목을 선택해 주세요.')
                return;
            }

            if (!confirm('선택한 항목을 등록하시겠습니까?')) {
                return;
            }

            callback(selectedIds);
        });
    };

    return {
        open,
        close,
        onRegister,
    };
};

export default createInspectionItemMultipleListModal;
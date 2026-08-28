import checkbox from "../../common/checkbox.js";
import ajax from "../../common/ajax.js";
import createPartnerContactCreateModal from "../../domain/partner-contact/modal/form/create.js";
import createPartnerContactEditModal from "../../domain/partner-contact/modal/form/edit.js";

document.addEventListener('DOMContentLoaded', function () {

    const checkboxGroup = document.querySelector('#partner-contact-table');
    const deleteButton = document.querySelector('#contact-delete-button');
    const createButton = document.querySelector('#contact-create-button');
    const editLinks = document.querySelectorAll('.contact-edit-link');

    checkbox.init(checkboxGroup);
    const contactCreateModal = createPartnerContactCreateModal();
    const contactEditModal = createPartnerContactEditModal();

    /* 담당자 등록 모달 */
    createButton.addEventListener('click', function (e) {
        const {partnerId} = e.currentTarget.dataset;

        contactCreateModal.open(partnerId);
    });

    /* 담당자 수정 모달 */
    editLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            const {id} = e.currentTarget.dataset;

            contactEditModal.open(id);
        });
    });

    /* 담당자 삭제 */
    document.querySelector('#contact-delete-button')
        .addEventListener('click', async function () {
            const selectedIds = checkbox.getCheckedValues(checkboxGroup);

            if (!selectedIds.length) {
                alert('항목을 선택해 주세요.');
                return;
            }

            if (!confirm('정말 삭제하시겠습니까?')) {
                return;
            }

            deleteButton.disabled = true;

            try {
                const response = await ajax.delete('/partner-contacts', selectedIds);

                alert(response.message);

                location.reload();

            } catch (xhr) {
                alert(xhr.responseJSON.message);
            }
        });
});
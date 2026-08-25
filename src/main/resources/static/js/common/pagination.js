import ajax from "./ajax.js";

const pagination = {
    bindEvents(container, onLoad) {
        container.addEventListener('click', async function (e) {
            const link = e.target.closest('.app-pagination a');

            if (!link || !container.contains(link)) {
                return;
            }

            e.preventDefault();

            try {
                const response = await ajax.get(link.href);

                onLoad(response);

            } catch (xhr) {
                alert(xhr.responseJSON.message);
            }
        });
    },
};

export default pagination;
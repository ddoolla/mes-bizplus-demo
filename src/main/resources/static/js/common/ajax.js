window.Mes = window.Mes || {};

/**
 * Ajax.delete('/users', selectedIds)
 *      .done(function (response) {
 *
 *          alert(response.message);
 *          ...
 *
 *      }).fail(function (xhr) {
 *
 *          console.log(xhr.status);
 *          alert(xhr.responseJSON.message);
 *          ...
 *
 *      })...
 */

// csrf 헤더, 토큰
const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;

Mes.Ajax = {

    get(url, data) {
        return $.ajax({
            url: url,
            method: 'GET',
            data: data,
        });
    },

    post(url, data) {

        const formData = data instanceof FormData;

        return $.ajax({
            url: url,
            method: 'POST',
            contentType: formData ? false : 'application/json',
            processData: !formData,
            data: formData ? data : JSON.stringify(data),
            headers: {
                [csrfHeader]: csrfToken,
            }
        });
    },

    put(url, data) {

        const formData = data instanceof FormData;

        return $.ajax({
            url: url,
            method: 'PUT',
            contentType: formData ? false : 'application/json',
            processData: !formData,
            data: formData ? data : JSON.stringify(data),
            headers: {
                [csrfHeader]: csrfToken,
            }
        });
    },

    delete(url, data) {

        const formData = data instanceof FormData;

        return $.ajax({
            url: url,
            method: 'DELETE',
            contentType: formData ? false : 'application/json',
            processData: !formData,
            data: formData ? data : JSON.stringify(data),
            headers: {
                [csrfHeader]: csrfToken,
            }
        });
    },
};
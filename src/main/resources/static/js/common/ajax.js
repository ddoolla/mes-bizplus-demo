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
window.Ajax = {

    delete(url, data) {

        // csrf 헤더, 토큰
        const header = document.querySelector('meta[name="_csrf_header"]')?.content;
        const token = document.querySelector('meta[name="_csrf"]')?.content;

        return $.ajax({
            url: url,
            method: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(data),
            headers: {
                [header]: token,
            }
        });
    },
};
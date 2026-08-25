// csrf 헤더, 토큰
const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;

const ajax = {
    request(options) {
        return $.ajax(options)
            .catch(function (xhr) {
                if (xhr.status === 401) {
                    const message = xhr.responseJSON?.message || '세션이 만료되었습니다. 다시 로그인 해주세요.';

                    alert(message);
                    window.location.href = '/login';
                    // 세션 만료는 여기서 예외처리 완료
                    return;
                }
                // 그 외 오류는 호출부 catch로 전달
                throw xhr;
            });
    },
    get(url, data) {
        return this.request({
            url: url,
            method: 'GET',
            data: data,
        });
    },
    post(url, data) {
        const formData = data instanceof FormData;

        return this.request({
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

        return this.request({
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

        return this.request({
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

export default ajax;
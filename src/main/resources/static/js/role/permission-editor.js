document.addEventListener('DOMContentLoaded', function () {

    /* 진입 시 전체 체크박스 업데이트 */
    document.querySelectorAll('tr').forEach(row => {

        const allCheck = row.querySelector('.menu-all-check');

        if (!allCheck) {
            return;
        }

        const permissions = row.querySelectorAll('.permission-check');

        const checked = row.querySelectorAll('.permission-check:checked');

        allCheck.checked = permissions.length > 0 && permissions.length === checked.length;
    });

    /* 전체 체크박스 변경 시 */
    document.querySelectorAll('.menu-all-check').forEach(allCheck => {

        allCheck.addEventListener('change', function () {

            const row = this.closest('tr');

            row.querySelectorAll('.permission-check').forEach(permission => {

                permission.checked = this.checked;
            });
        });
    });

    /* 개별 체크박스 변경 시 */
    document.querySelectorAll('.permission-check').forEach(permission => {

        permission.addEventListener('change', function () {

            const row = this.closest('tr');

            const permissions = row.querySelectorAll('.permission-check');

            const checked = row.querySelectorAll('.permission-check:checked');

            row.querySelector('.menu-all-check').checked = permissions.length === checked.length;
        });
    });
});
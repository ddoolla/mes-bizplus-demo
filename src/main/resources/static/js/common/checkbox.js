/**
 * <div class="some-group">
 *     <table>
 *         ...
 *         <th>
 *             <input type="checkbox" class="check-all">
 *         </th>
 *         ...
 *         <td>
 *             <input type="checkbox" class="check-item">
 *         </td>
 *     </table>
 * </div>
 */
window.Checkbox = {

    init(group) {

        const checkAll = group.querySelector('.check-all');
        const checkboxes = group.querySelectorAll('.check-item');

        checkAll.addEventListener('change', function () {

            checkboxes.forEach(checkbox => {
                checkbox.checked = this.checked;
            });

            checkAll.indeterminate = false;
        });

        checkboxes.forEach(checkbox => {

            checkbox.addEventListener('change', () => {

                const checkedCount = group.querySelectorAll('.check-item:checked').length;
                const totalCount = checkboxes.length;

                checkAll.checked = checkedCount === totalCount;
                checkAll.indeterminate = checkedCount > 0 && checkedCount < totalCount;
            });
        });
    },

    getCheckedValues(group) {

        return Array.from(group.querySelectorAll('.check-item:checked'))
            .map(checkbox => checkbox.value);
    }
};
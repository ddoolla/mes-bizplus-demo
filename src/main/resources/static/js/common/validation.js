$.validator.addMethod('positive', function (value, element) {
    return this.optional(element) || Number(value) > 0;
}, '0보다 큰 값을 입력해 주세요.');

$.validator.addMethod("notEqualTo", function (value, element, param) {
    return this.optional(element) || value !== $(param).val();
}, "같은 값은 입력할 수 없습니다.");
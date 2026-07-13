package com.bizplus.mes.domain.permission;

import lombok.Getter;

@Getter
public enum PermissionAction {

    CREATE,
    READ,
    UPDATE,
    DELETE
    // EXCEL_UPLOAD 등 추가 가능
}

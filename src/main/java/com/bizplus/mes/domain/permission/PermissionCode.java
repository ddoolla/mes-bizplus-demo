package com.bizplus.mes.domain.permission;

import com.bizplus.mes.domain.menu.MenuCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionCode {

    ROLE_READ(MenuCode.ROLE, "권한 조회", PermissionAction.READ),
    ROLE_CREATE(MenuCode.ROLE, "권한 생성", PermissionAction.CREATE),
    ROLE_UPDATE(MenuCode.ROLE, "권한 수정", PermissionAction.UPDATE),
    ROLE_DELETE(MenuCode.ROLE, "권한 삭제", PermissionAction.DELETE),

    USER_READ(MenuCode.USER, "사용자 조회", PermissionAction.READ),
    USER_CREATE(MenuCode.USER, "사용자 생성", PermissionAction.CREATE),
    USER_UPDATE(MenuCode.USER, "사용자 수정", PermissionAction.UPDATE),
    USER_DELETE(MenuCode.USER, "사용자 삭제", PermissionAction.DELETE),

    ACCOUNT_READ(MenuCode.ACCOUNT, "거래처 조회", PermissionAction.READ),
    ACCOUNT_CREATE(MenuCode.ACCOUNT, "거래처 생성", PermissionAction.CREATE),
    ACCOUNT_UPDATE(MenuCode.ACCOUNT, "거래처 수정", PermissionAction.UPDATE),
    ACCOUNT_DELETE(MenuCode.ACCOUNT, "거래처 삭제", PermissionAction.DELETE);

    private final MenuCode menu;
    private final String name;
    private final PermissionAction action;
}

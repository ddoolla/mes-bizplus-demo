package com.bizplus.mes.domain.permission;

import com.bizplus.mes.domain.menu.MenuInitialData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionInitialData {

    ROLE_READ(MenuInitialData.ROLE, "권한 조회", PermissionAction.READ),
    ROLE_CREATE(MenuInitialData.ROLE, "권한 생성", PermissionAction.CREATE),
    ROLE_UPDATE(MenuInitialData.ROLE, "권한 수정", PermissionAction.UPDATE),
    ROLE_DELETE(MenuInitialData.ROLE, "권한 삭제", PermissionAction.DELETE),

    USER_READ(MenuInitialData.USER, "사용자 조회", PermissionAction.READ),
    USER_CREATE(MenuInitialData.USER, "사용자 생성", PermissionAction.CREATE),
    USER_UPDATE(MenuInitialData.USER, "사용자 수정", PermissionAction.UPDATE),
    USER_DELETE(MenuInitialData.USER, "사용자 삭제", PermissionAction.DELETE),

    ACCOUNT_READ(MenuInitialData.ACCOUNT, "거래처 조회", PermissionAction.READ),
    ACCOUNT_CREATE(MenuInitialData.ACCOUNT, "거래처 생성", PermissionAction.CREATE),
    ACCOUNT_UPDATE(MenuInitialData.ACCOUNT, "거래처 수정", PermissionAction.UPDATE),
    ACCOUNT_DELETE(MenuInitialData.ACCOUNT, "거래처 삭제", PermissionAction.DELETE);

    private final MenuInitialData menu;
    private final String name;
    private final PermissionAction action;
}

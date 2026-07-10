package com.bizplus.mes.domain.permission;

import com.bizplus.mes.domain.menu.MenuSeed;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionSeed {

    ROLE_READ(MenuSeed.ROLE, "권한 조회", PermissionAction.READ),
    ROLE_CREATE(MenuSeed.ROLE, "권한 생성", PermissionAction.CREATE),
    ROLE_UPDATE(MenuSeed.ROLE, "권한 수정", PermissionAction.UPDATE),
    ROLE_DELETE(MenuSeed.ROLE, "권한 삭제", PermissionAction.DELETE),

    USER_READ(MenuSeed.USER, "사용자 조회", PermissionAction.READ),
    USER_CREATE(MenuSeed.USER, "사용자 생성", PermissionAction.CREATE),
    USER_UPDATE(MenuSeed.USER, "사용자 수정", PermissionAction.UPDATE),
    USER_DELETE(MenuSeed.USER, "사용자 삭제", PermissionAction.DELETE);

    private final MenuSeed menu;
    private final String name;
    private final PermissionAction action;
}

package com.bizplus.mes.domain.permission;

import com.bizplus.mes.domain.menu.MenuCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionCode {

    ROLE_READ(MenuCode.ROLE, "권한 조회", PermissionAction.READ),
    ROLE_CREATE(MenuCode.ROLE, "권한 등록", PermissionAction.CREATE),
    ROLE_UPDATE(MenuCode.ROLE, "권한 수정", PermissionAction.UPDATE),
    ROLE_DELETE(MenuCode.ROLE, "권한 삭제", PermissionAction.DELETE),

    USER_READ(MenuCode.USER, "사용자 조회", PermissionAction.READ),
    USER_CREATE(MenuCode.USER, "사용자 등록", PermissionAction.CREATE),
    USER_UPDATE(MenuCode.USER, "사용자 수정", PermissionAction.UPDATE),
    USER_DELETE(MenuCode.USER, "사용자 삭제", PermissionAction.DELETE),

    USER_LOG_READ(MenuCode.USER_LOG, "사용자 로그 조회", PermissionAction.READ),

    COMMON_CODE_READ(MenuCode.COMMON_CODE, "공통코드 조회", PermissionAction.READ),
    COMMON_CODE_CREATE(MenuCode.COMMON_CODE, "공통코드 등록", PermissionAction.CREATE),
    COMMON_CODE_UPDATE(MenuCode.COMMON_CODE, "공통코드 수정", PermissionAction.UPDATE),
    COMMON_CODE_DELETE(MenuCode.COMMON_CODE, "공통코드 삭제", PermissionAction.DELETE),

    UOM_READ(MenuCode.UOM, "단위 조회", PermissionAction.READ),
    UOM_CREATE(MenuCode.UOM, "단위 등록", PermissionAction.CREATE),
    UOM_UPDATE(MenuCode.UOM, "단위 수정", PermissionAction.UPDATE),
    UOM_DELETE(MenuCode.UOM, "단위 삭제", PermissionAction.DELETE),

    PARTNER_READ(MenuCode.PARTNER, "거래처 조회", PermissionAction.READ),
    PARTNER_CREATE(MenuCode.PARTNER, "거래처 등록", PermissionAction.CREATE),
    PARTNER_UPDATE(MenuCode.PARTNER, "거래처 수정", PermissionAction.UPDATE),
    PARTNER_DELETE(MenuCode.PARTNER, "거래처 삭제", PermissionAction.DELETE);

    private final MenuCode menu;
    private final String name;
    private final PermissionAction action;
}

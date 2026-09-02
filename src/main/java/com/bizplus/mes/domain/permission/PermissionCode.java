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
    PARTNER_DELETE(MenuCode.PARTNER, "거래처 삭제", PermissionAction.DELETE),

    ITEM_READ(MenuCode.ITEM, "품목 조회", PermissionAction.READ),
    ITEM_CREATE(MenuCode.ITEM, "품목 등록", PermissionAction.CREATE),
    ITEM_UPDATE(MenuCode.ITEM, "품목 수정", PermissionAction.UPDATE),
    ITEM_DELETE(MenuCode.ITEM, "품목 삭제", PermissionAction.DELETE),

    BOM_READ(MenuCode.ITEM, "BOM 조회", PermissionAction.READ),
    BOM_CREATE(MenuCode.ITEM, "BOM 등록", PermissionAction.CREATE),
    BOM_UPDATE(MenuCode.ITEM, "BOM 수정", PermissionAction.UPDATE),
    BOM_DELETE(MenuCode.ITEM, "BOM 삭제", PermissionAction.DELETE),

    PROCESS_READ(MenuCode.PROCESS, "공정 조회", PermissionAction.READ),
    PROCESS_CREATE(MenuCode.PROCESS, "공정 등록", PermissionAction.CREATE),
    PROCESS_UPDATE(MenuCode.PROCESS, "공정 수정", PermissionAction.UPDATE),
    PROCESS_DELETE(MenuCode.PROCESS, "공정 삭제", PermissionAction.DELETE),

    ROUTING_READ(MenuCode.ROUTING, "제품 공정 조회", PermissionAction.READ),
    ROUTING_CREATE(MenuCode.ROUTING, "제품 공정 등록", PermissionAction.CREATE),
    ROUTING_UPDATE(MenuCode.ROUTING, "제품 공정 수정", PermissionAction.UPDATE),
    ROUTING_DELETE(MenuCode.ROUTING, "제품 공정 삭제", PermissionAction.DELETE),

    EQUIPMENT_READ(MenuCode.EQUIPMENT, "설비 조회", PermissionAction.READ),
    EQUIPMENT_CREATE(MenuCode.EQUIPMENT, "설비 등록", PermissionAction.CREATE),
    EQUIPMENT_UPDATE(MenuCode.EQUIPMENT, "설비 수정", PermissionAction.UPDATE),
    EQUIPMENT_DELETE(MenuCode.EQUIPMENT, "설비 삭제", PermissionAction.DELETE),

    WORKER_READ(MenuCode.WORKER, "작업자 조회", PermissionAction.READ),
    WORKER_CREATE(MenuCode.WORKER, "작업자 등록", PermissionAction.CREATE),
    WORKER_UPDATE(MenuCode.WORKER, "작업자 수정", PermissionAction.UPDATE),
    WORKER_DELETE(MenuCode.WORKER, "작업자 삭제", PermissionAction.DELETE),

    INSPECTION_ITEM_READ(MenuCode.INSPECTION_ITEM, "검사항목 조회", PermissionAction.READ),
    INSPECTION_ITEM_CREATE(MenuCode.INSPECTION_ITEM, "검사항목 등록", PermissionAction.CREATE),
    INSPECTION_ITEM_UPDATE(MenuCode.INSPECTION_ITEM, "검사항목 수정", PermissionAction.UPDATE),
    INSPECTION_ITEM_DELETE(MenuCode.INSPECTION_ITEM, "검사항목 삭제", PermissionAction.DELETE),

    INSPECTION_SPEC_READ(MenuCode.INSPECTION_SPEC, "검사기준 조회", PermissionAction.READ),
    INSPECTION_SPEC_CREATE(MenuCode.INSPECTION_SPEC, "검사기준 등록", PermissionAction.CREATE),
    INSPECTION_SPEC_UPDATE(MenuCode.INSPECTION_SPEC, "검사기준 수정", PermissionAction.UPDATE),
    INSPECTION_SPEC_DELETE(MenuCode.INSPECTION_SPEC, "검사기준 삭제", PermissionAction.DELETE),

    ITEM_INVENTORY_READ(MenuCode.ITEM_INVENTORY, "품목 재고 조회", PermissionAction.READ);

    private final MenuCode menu;
    private final String name;
    private final PermissionAction action;
}

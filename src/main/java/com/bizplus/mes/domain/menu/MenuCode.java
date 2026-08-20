package com.bizplus.mes.domain.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuCode {

    SYSTEM(null, "시스템 관리", MenuType.GROUP, null, 10),
    ROLE(SYSTEM, "권한 관리", MenuType.MENU, "/roles", 20),
    USER(SYSTEM, "사용자 관리", MenuType.MENU, "/users", 30),
    USER_LOG(SYSTEM, "사용자 로그 관리", MenuType.MENU, "/user-logs/auth", 40),

    MASTER(null, "기준 정보 관리", MenuType.GROUP, null, 50),
    COMMON_CODE(MASTER, "공통 코드 관리", MenuType.MENU, "/code-groups", 60),
    UOM(MASTER, "단위 관리", MenuType.MENU, "/uoms", 70),
    PARTNER(MASTER, "거래처 관리", MenuType.MENU, "/partners", 80),
    ITEM(MASTER, "품목 관리", MenuType.MENU, "/items", 90),
    BOM(MASTER, "BOM 관리", MenuType.MENU, "/boms", 100),
    PROCESS(MASTER, "공정 관리", MenuType.MENU, "/processes", 110),
    ROUTING(MASTER, "제품 공정 관리", MenuType.MENU, "/routings", 120),

    INVENTORY(null, "재고 관리", MenuType.GROUP, null, 130),
    ITEM_INVENTORY(INVENTORY, "재고 조회", MenuType.MENU, "/inventories", 140);

    private final MenuCode parent;
    private final String name;
    private final MenuType type;
    private final String path;
    private final Integer sortOrder;
}

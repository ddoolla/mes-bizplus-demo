package com.bizplus.mes.domain.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuCode {

    SYSTEM(null, "시스템 관리", MenuType.GROUP, null, 10),
    ROLE(SYSTEM, "권한 관리", MenuType.MENU, "/roles", 20),
    USER(SYSTEM, "사용자 관리", MenuType.MENU, "/users", 30),

    MASTER(null, "기준 정보 관리", MenuType.GROUP, null, 40),
    COMMON_CODE(MASTER, "공통 코드 관리", MenuType.MENU, "/code-groups", 50),
    ACCOUNT(MASTER, "거래처 관리", MenuType.MENU, "/accounts", 60);

    private final MenuCode parent;
    private final String name;
    private final MenuType type;
    private final String path;
    private final Integer sortOrder;
}

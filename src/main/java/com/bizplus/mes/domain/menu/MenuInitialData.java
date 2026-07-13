package com.bizplus.mes.domain.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuInitialData {

    SYSTEM(null, "SYSTEM", "시스템 관리", MenuType.GROUP, null, 10),
    ROLE(SYSTEM, "ROLE", "권한 관리", MenuType.MENU, "/roles", 20),
    USER(SYSTEM, "USER", "사용자 관리", MenuType.MENU, "/users", 30),

    MASTER(null, "MASTER", "기준 정보 관리", MenuType.GROUP, null, 40),
    ACCOUNT(MASTER, "ACCOUNT", "거래처 정보 관리", MenuType.MENU, "/accounts", 50);

    private final MenuInitialData parentMenu;
    private final String code;
    private final String name;
    private final MenuType type;
    private final String path;
    private final Integer sortOrder;
}

package com.bizplus.mes.domain.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuSeed {

    SYSTEM(null, "SYSTEM", "시스템 관리", MenuType.GROUP, null, 10),
    ROLE("SYSTEM", "ROLE", "권한 관리", MenuType.MENU, "/roles", 20),
    USER("SYSTEM", "USER", "사용자 관리", MenuType.MENU, "/users", 30);

    private final String parentMenu;
    private final String code;
    private final String name;
    private final MenuType type;
    private final String path;
    private final Integer sortOrder;
}

package com.bizplus.mes.domain.code.group;

import com.bizplus.mes.domain.menu.MenuCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodeGroupKey {

    DEPARTMENT(MenuCode.USER, "부서", 10),
    POSITION(MenuCode.USER, "직책", 20),
    ITEM_CATEGORY(MenuCode.ITEM, "품목 카테고리", 30),
    EQUIPMENT_TYPE(MenuCode.EQUIPMENT, "설비 유형", 40);

    private final MenuCode menu;
    private final String name;
    private final Integer sortOrder;
}

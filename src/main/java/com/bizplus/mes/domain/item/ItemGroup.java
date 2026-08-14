package com.bizplus.mes.domain.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemGroup {

    PRODUCT(
            "제품",
            new ItemType[]{
                    ItemType.FINISHED_GOODS,
                    ItemType.SEMI_FINISHED_GOODS
            }),
    MATERIAL(
            "자재",
            new ItemType[]{
                    ItemType.RAW_MATERIAL,
                    ItemType.SUB_MATERIAL
            });

    private final String description;
    private final ItemType[] types;
}

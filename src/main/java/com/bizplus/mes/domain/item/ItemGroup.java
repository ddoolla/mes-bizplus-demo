package com.bizplus.mes.domain.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ItemGroup {

    PRODUCT(
            "제품",
            List.of(
                    ItemType.FINISHED_GOODS,
                    ItemType.SEMI_FINISHED_GOODS
            )),
    MATERIAL(
            "자재",
            List.of(
                    ItemType.RAW_MATERIAL,
                    ItemType.SUB_MATERIAL
            )),
    FINISHED_GOODS_BOM_ITEM(
            "완제품 BOM 구성 품목",
            List.of(
                    ItemType.FINISHED_GOODS,
                    ItemType.SEMI_FINISHED_GOODS,
                    ItemType.RAW_MATERIAL,
                    ItemType.SUB_MATERIAL
            )),
    SEMI_FINISHED_GOODS_BOM_ITEM(
            "반제품 BOM 구성 품목",
            List.of(
                    ItemType.SEMI_FINISHED_GOODS,
                    ItemType.RAW_MATERIAL,
                    ItemType.SUB_MATERIAL
            ));

    private final String description;
    private final List<ItemType> types;
}

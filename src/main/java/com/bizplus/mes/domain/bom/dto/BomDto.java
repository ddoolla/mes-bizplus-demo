package com.bizplus.mes.domain.bom.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class BomDto {

    private final Long id;
    private final String code;
    private final String name;
    private final String version;
    private final String remark;
    private final Boolean isDefault;
    private final ItemInfo item;

    @QueryProjection
    public BomDto(Long id,
                  String code,
                  String name,
                  String version,
                  String remark,
                  Boolean isDefault,
                  Long itemId,
                  String itemCode,
                  String itemName,
                  ItemType itemType,
                  String itemSpec) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.version = version;
        this.remark = remark;
        this.isDefault = isDefault;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemType,
                itemSpec
        );
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           ItemType type,
                           String specification) {
    }
}

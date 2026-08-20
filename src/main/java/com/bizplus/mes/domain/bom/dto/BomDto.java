package com.bizplus.mes.domain.bom.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class BomDto {

    private final Long id;
    private final String code;
    private final String name;
    private final String version;
    private final boolean primary;
    private final String remark;
    private final ItemInfo item;

    @QueryProjection
    public BomDto(Long id,
                  String code,
                  String name,
                  String version,
                  boolean primary,
                  String remark,
                  Long itemId,
                  String itemCode,
                  String itemName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = primary;
        this.remark = remark;
        this.item = new ItemInfo(itemId, itemCode, itemName);
    }

    public record ItemInfo(Long id, String code, String name) {
    }
}

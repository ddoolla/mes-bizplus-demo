package com.bizplus.mes.domain.routing.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class RoutingDto {

    private final Long id;
    private final String code;
    private final String name;
    private final String version;
    private final boolean primary;
    private final String description;
    private final ItemInfo item;

    @QueryProjection
    public RoutingDto(Long id,
                      String code,
                      String name,
                      String version,
                      boolean primary,
                      String description,
                      Long itemId,
                      String itemCode,
                      String itemName,
                      String itemCategory,
                      ItemType itemType) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = primary;
        this.description = description;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemCategory,
                itemType);
    }

    public record ItemInfo(
            Long id,
            String code,
            String name,
            String category,
            ItemType type
    ) {
    }
}

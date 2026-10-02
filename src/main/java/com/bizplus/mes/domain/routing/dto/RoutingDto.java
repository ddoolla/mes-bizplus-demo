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
    private final String description;
    private final Boolean isDefault;
    private final ItemInfo item;

    @QueryProjection
    public RoutingDto(Long id,
                      String code,
                      String name,
                      String version,
                      String description,
                      Boolean isDefault,
                      Long itemId,
                      Long defaultBomId,
                      String itemCode,
                      String itemName,
                      String itemCategory,
                      ItemType itemType) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.version = version;
        this.description = description;
        this.isDefault = isDefault;
        this.item = new ItemInfo(
                itemId,
                defaultBomId,
                itemCode,
                itemName,
                itemCategory,
                itemType);
    }

    public record ItemInfo(
            Long id,
            Long defaultBomId,
            String code,
            String name,
            String category,
            ItemType type
    ) {
    }
}

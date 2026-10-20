package com.bizplus.mes.domain.lot.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class LotDto {

    private final Long id;
    private final String no;
    private final ItemInfo item;
    private final InventoryInfo inventory;

    @QueryProjection
    public LotDto(Long id,
                  String no,
                  Long itemId,
                  String itemCode,
                  String itemName,
                  ItemType itemType,
                  String itemSpec,
                  Long inventoryId,
                  BigDecimal quantity) {
        this.id = id;
        this.no = no;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemType,
                itemSpec
        );
        this.inventory = new InventoryInfo(
                inventoryId,
                quantity
        );
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           ItemType type,
                           String specification) {
    }

    public record InventoryInfo(Long id,
                                BigDecimal quantity) {

    }
}

package com.bizplus.mes.domain.inventory.adjustment.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class InventoryAdjustmentDto {

    private final Long id;
    private final LocalDate date;
    private final BigDecimal beforeQuantity;
    private final BigDecimal afterQuantity;
    private final String reason;
    private final ItemInfo item;
    private final LotInfo lot;

    @QueryProjection
    public InventoryAdjustmentDto(Long id,
                                  LocalDate date,
                                  BigDecimal beforeQuantity,
                                  BigDecimal afterQuantity,
                                  String reason,
                                  Long itemId,
                                  String itemCode,
                                  String itemName,
                                  ItemType itemType,
                                  String itemSpec,
                                  String itemUomCode,
                                  Integer itemUomScale,
                                  Long lotId,
                                  String lotNo) {
        this.id = id;
        this.date = date;
        this.beforeQuantity = beforeQuantity;
        this.afterQuantity = afterQuantity;
        this.reason = reason;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemType,
                itemSpec,
                new ItemUomInfo(itemUomCode, itemUomScale)
        );
        this.lot = new LotInfo(lotId, lotNo);
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           ItemType type,
                           String itemSpec,
                           ItemUomInfo uom) {
    }

    public record ItemUomInfo(String code, Integer scale) {
    }

    public record LotInfo(Long id, String no) {
    }
}

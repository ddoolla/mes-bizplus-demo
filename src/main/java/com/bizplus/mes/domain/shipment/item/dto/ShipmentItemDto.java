package com.bizplus.mes.domain.shipment.item.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ShipmentItemDto {

    private final Long id;
    private final BigDecimal quantity;
    private final ItemInfo item;
    private final LotInfo lot;

    @QueryProjection
    public ShipmentItemDto(Long id,
                           BigDecimal quantity,
                           Long itemId,
                           String itemCode,
                           String itemName,
                           ItemType itemType,
                           String itemSpec,
                           Long itemUomId,
                           String itemUomCode,
                           Integer itemUomScale,
                           Long lotId,
                           String lotNo) {
        this.id = id;
        this.quantity = quantity;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemType,
                itemSpec,
                new ItemUomInfo(itemUomId, itemUomCode, itemUomScale));
        this.lot = new LotInfo(
                lotId,
                lotNo
        );
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           ItemType type,
                           String specification,
                           ItemUomInfo uom) {
    }

    public record ItemUomInfo(Long id, String code, Integer scale) {
    }

    public record LotInfo(Long id, String no) {
    }
}

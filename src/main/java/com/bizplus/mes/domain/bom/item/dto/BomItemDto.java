package com.bizplus.mes.domain.bom.item.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BomItemDto {

    private final Long id;
    private final ItemInfo item;
    private final UomInfo uom;
    private final BigDecimal quantity;

    @QueryProjection
    public BomItemDto(Long id,
                      Long itemId,
                      String itemCode,
                      String itemName,
                      String itemCategory,
                      ItemType itemType,
                      String itemSpec,
                      Long uomId,
                      String uomCode,
                      Integer decimalPlaces,
                      BigDecimal quantity) {
        this.id = id;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemCategory,
                itemType,
                itemSpec
        );
        this.uom = new UomInfo(
                uomId,
                uomCode,
                decimalPlaces
        );
        this.quantity = quantity;
    }

    public record UomInfo(
            Long id,
            String code,
            Integer decimalPlaces
    ) {
    }

    public record ItemInfo(
            Long id,
            String code,
            String name,
            String category,
            ItemType type,
            String specification
    ) {
    }
}

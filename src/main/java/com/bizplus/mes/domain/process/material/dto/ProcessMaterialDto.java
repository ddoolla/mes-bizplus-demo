package com.bizplus.mes.domain.process.material.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.process.material.ConsumptionMethod;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProcessMaterialDto {

    private final Long id;
    private final BigDecimal quantity;
    private final ConsumptionMethod consumptionMethod;
    private final ItemInfo item;
    private final UomInfo uom;

    @QueryProjection
    public ProcessMaterialDto(Long id,
                              BigDecimal quantity,
                              ConsumptionMethod consumptionMethod,
                              Long itemId,
                              String itemCode,
                              String itemName,
                              String itemCategory,
                              ItemType itemType,
                              String itemSpec,
                              Long itemUomId,
                              String itemUomCode,
                              Integer itemUomScale,
                              Long uomId,
                              String uomCode,
                              Integer uomScale) {
        this.id = id;
        this.quantity = quantity;
        this.consumptionMethod = consumptionMethod;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemCategory,
                itemType,
                itemSpec,
                new ItemUomInfo(itemUomId, itemUomCode, itemUomScale)
        );
        this.uom = new UomInfo(uomId, uomCode, uomScale);
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           String category,
                           ItemType type,
                           String specification,
                           ItemUomInfo uom) {
    }

    public record ItemUomInfo(Long id, String code, Integer scale) {
    }

    public record UomInfo(Long id, String code, Integer scale) {
    }
}

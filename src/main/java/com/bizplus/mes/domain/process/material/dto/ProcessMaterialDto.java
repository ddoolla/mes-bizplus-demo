package com.bizplus.mes.domain.process.material.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.process.material.ConsumptionMethod;
import com.bizplus.mes.domain.uom.UomType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProcessMaterialDto {

    private final Long id;
    private final BigDecimal quantity;
    private final ConsumptionMethod consumptionMethod;
    private final ItemInfo item;
    private final UomInfo consumptionUom;

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
                              Long stockUomId,
                              String stockUomCode,
                              String stockUomName,
                              UomType stockUomType,
                              Integer stockUomScale,
                              Long consumptionUomId,
                              String consumptionUomCode,
                              String consumptionUomName,
                              UomType consumptionUomType,
                              Integer consumptionUomScale) {
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
                new UomInfo(
                        stockUomId,
                        stockUomCode,
                        stockUomName,
                        stockUomType,
                        stockUomScale
                )
        );
        this.consumptionUom = new UomInfo(
                consumptionUomId,
                consumptionUomCode,
                consumptionUomName,
                consumptionUomType,
                consumptionUomScale
        );
    }

    public record ItemInfo(
            Long id,
            String code,
            String name,
            String category,
            ItemType type,
            String specification,
            UomInfo stockUom
    ) {
    }

    public record UomInfo(
            Long id,
            String code,
            String name,
            UomType type,
            Integer scale
    ) {
    }
}

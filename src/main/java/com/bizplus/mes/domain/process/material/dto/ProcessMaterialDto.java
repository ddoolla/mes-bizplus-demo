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
    private final UomInfo uom;
    // todo 환산 가능 uom list 같이 가야할 듯

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
                              Long uomId,
                              String uomCode,
                              String uomName,
                              UomType uomType,
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
                itemSpec
        );
        this.uom = new UomInfo(
                uomId,
                uomCode,
                uomName,
                uomType,
                uomScale
        );
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

    public record UomInfo(
            Long id,
            String code,
            String name,
            UomType type,
            Integer scale
    ) {
    }
}

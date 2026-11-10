package com.bizplus.mes.domain.material.input.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MaterialInputProductionLotDto {

    private final Long lotId;
    private final String lotNo;
    private final String itemCode;
    private final String itemName;
    private final ItemType itemType;
    private final String itemSpec;
    private final String uomCode;
    private final Integer uomScale;
    private final BigDecimal productionQuantity;

    @QueryProjection
    public MaterialInputProductionLotDto(Long lotId,
                                         String lotNo,
                                         String itemCode,
                                         String itemName,
                                         ItemType itemType,
                                         String itemSpec,
                                         String uomCode,
                                         Integer uomScale,
                                         BigDecimal productionQuantity) {
        this.lotId = lotId;
        this.lotNo = lotNo;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemSpec = itemSpec;
        this.uomCode = uomCode;
        this.uomScale = uomScale;
        this.productionQuantity = productionQuantity;
    }
}

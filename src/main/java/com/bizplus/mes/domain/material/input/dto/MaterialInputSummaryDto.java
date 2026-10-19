package com.bizplus.mes.domain.material.input.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MaterialInputSummaryDto {

    private final ItemInfo item;
    private final UomInfo uom;
    private final BigDecimal totalQuantity;

    @QueryProjection
    public MaterialInputSummaryDto(Long itemId,
                                   String itemCode,
                                   String itemName,
                                   String itemSpec,
                                   boolean lotManaged,
                                   String uomCode,
                                   Integer uomScale,
                                   BigDecimal totalQuantity) {
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemSpec,
                lotManaged
        );
        this.uom = new UomInfo(
                uomCode,
                uomScale
        );
        this.totalQuantity = totalQuantity;
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           String specification,
                           boolean lotManaged) {
    }

    public record UomInfo(String code,
                          Integer scale) {
    }
}

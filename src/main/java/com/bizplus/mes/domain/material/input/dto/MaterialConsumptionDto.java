package com.bizplus.mes.domain.material.input.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MaterialConsumptionDto {

    private final ItemInfo item;
    private final UomInfo uom;
    private final BigDecimal requiredQuantity;
    private final BigDecimal inputQuantity;
    private final BigDecimal remainingQuantity;

    public MaterialConsumptionDto(Long itemId,
                                  String itemCode,
                                  String itemName,
                                  String itemSpec,
                                  boolean lotManaged,
                                  Long uomId,
                                  String uomCode,
                                  Integer uomScale,
                                  BigDecimal requiredQuantity,
                                  BigDecimal inputQuantity,
                                  BigDecimal remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemSpec,
                lotManaged
        );
        this.uom = new UomInfo(
                uomId,
                uomCode,
                uomScale
        );
        this.requiredQuantity = requiredQuantity;
        this.inputQuantity = inputQuantity;
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           String specification,
                           boolean lotManaged) {
    }

    public record UomInfo(Long id,
                          String code,
                          Integer scale) {
    }
}

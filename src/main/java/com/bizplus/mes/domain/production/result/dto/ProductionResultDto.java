package com.bizplus.mes.domain.production.result.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductionResultDto {

    private final Long id;
    private final BigDecimal totalQuantity;
    private final BigDecimal goodQuantity;
    private final BigDecimal defectQuantity;
    private final LocalDateTime completedAt;
    private final ProductionOrderInfo productionOrder;
    private final ItemInfo item;

    @QueryProjection
    public ProductionResultDto(Long id,
                               BigDecimal totalQuantity,
                               BigDecimal goodQuantity,
                               BigDecimal defectQuantity,
                               LocalDateTime completedAt,
                               Long productionOrderId,
                               String productionOrderNo,
                               Long itemId,
                               String itemCode,
                               String itemName,
                               ItemType itemType,
                               String itemSpec,
                               Long itemUomId,
                               String itemUomCode,
                               Integer itemUomScale) {
        this.id = id;
        this.totalQuantity = totalQuantity;
        this.goodQuantity = goodQuantity;
        this.defectQuantity = defectQuantity;
        this.completedAt = completedAt;
        this.productionOrder = new ProductionOrderInfo(
                productionOrderId,
                productionOrderNo
        );
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemType,
                itemSpec,
                new ItemUomInfo(itemUomId, itemUomCode, itemUomScale)
        );
    }

    public record ProductionOrderInfo(Long id, String orderNo) {
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
}

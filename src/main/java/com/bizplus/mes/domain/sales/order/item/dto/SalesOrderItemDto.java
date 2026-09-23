package com.bizplus.mes.domain.sales.order.item.dto;

import com.bizplus.mes.domain.sales.order.item.SalesOrderItemStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SalesOrderItemDto {

    private final Long id;
    private final BigDecimal unitPrice;
    private final BigDecimal quantity;
    private final BigDecimal supplyAmount;
    private final BigDecimal taxAmount;
    private final BigDecimal totalAmount;
    private final SalesOrderItemStatus status;
    private final String remark;
    private final ItemInfo item;
    private final UomInfo uom;

    @QueryProjection
    public SalesOrderItemDto(Long id,
                             BigDecimal unitPrice,
                             BigDecimal quantity,
                             BigDecimal supplyAmount,
                             BigDecimal taxAmount,
                             BigDecimal totalAmount,
                             SalesOrderItemStatus status,
                             String remark,
                             Long itemId,
                             String itemCode,
                             String itemName,
                             String itemSpec,
                             Long uomId,
                             String uomCode,
                             Integer uomScale) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.supplyAmount = supplyAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.status = status;
        this.remark = remark;
        this.item = new ItemInfo(itemId, itemCode, itemName, itemSpec);
        this.uom = new UomInfo(uomId, uomCode, uomScale);
    }

    public record ItemInfo(
            Long id,
            String code,
            String name,
            String specification) {
    }

    public record UomInfo(
            Long id,
            String code,
            Integer scale
    ) {
    }
}

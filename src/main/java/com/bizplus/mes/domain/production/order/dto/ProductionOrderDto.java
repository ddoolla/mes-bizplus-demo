package com.bizplus.mes.domain.production.order.dto;

import com.bizplus.mes.domain.production.order.ProductionOrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class ProductionOrderDto {

    private final Long id;
    private final String orderNo;
    private final BigDecimal quantity;
    private final LocalDate dueDate;
    private final ProductionOrderStatus status;
    private final String remark;
    private final ItemInfo item;
    private final BomInfo bom;
    private final RoutingInfo routing;

    @QueryProjection
    public ProductionOrderDto(Long id,
                              String orderNo,
                              BigDecimal quantity,
                              LocalDate dueDate,
                              ProductionOrderStatus status,
                              String remark,
                              Long itemId,
                              String itemCode,
                              String itemName,
                              String itemSpec,
                              String itemUomCode,
                              Integer itemUomScale,
                              Long bomId,
                              String bomCode,
                              String bomName,
                              Long routingId,
                              String routingCode,
                              String routingName) {
        this.id = id;
        this.orderNo = orderNo;
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.status = status;
        this.remark = remark;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemSpec,
                new ItemUomInfo(itemUomCode, itemUomScale)
        );
        this.bom = new BomInfo(
                bomId,
                bomCode,
                bomName
        );
        this.routing = new RoutingInfo(
                routingId,
                routingCode,
                routingName
        );
    }

    public record ItemInfo(
            Long id,
            String code,
            String name,
            String specification,
            ItemUomInfo uom
    ) {
    }

    public record ItemUomInfo(
            String code,
            Integer scale
    ) {
    }

    public record BomInfo(
            Long id,
            String code,
            String name
    ) {
    }

    public record RoutingInfo(
            Long id,
            String code,
            String name
    ) {
    }
}

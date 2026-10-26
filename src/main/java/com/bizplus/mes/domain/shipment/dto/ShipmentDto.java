package com.bizplus.mes.domain.shipment.dto;

import com.bizplus.mes.domain.shipment.ShipmentStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ShipmentDto {

    private final Long id;
    private final String shipmentNo;
    private final LocalDate date;
    private final ShipmentStatus status;
    private final String remark;
    private final PartnerInfo partner;
    private final SalesOrderInfo salesOrder;

    @QueryProjection
    public ShipmentDto(Long id,
                       String shipmentNo,
                       LocalDate date,
                       ShipmentStatus status,
                       String remark,
                       Long partnerId,
                       String partnerName,
                       Long salesOrderId,
                       String salesOrderNo) {
        this.id = id;
        this.shipmentNo = shipmentNo;
        this.date = date;
        this.status = status;
        this.remark = remark;
        this.partner = new PartnerInfo(partnerId, partnerName);
        this.salesOrder = new SalesOrderInfo(salesOrderId, salesOrderNo);
    }

    public record PartnerInfo(Long id, String name) {
    }

    public record SalesOrderInfo(Long id, String orderNo) {
    }
}

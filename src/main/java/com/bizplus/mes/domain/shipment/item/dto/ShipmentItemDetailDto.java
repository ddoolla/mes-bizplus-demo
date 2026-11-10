package com.bizplus.mes.domain.shipment.item.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.shipment.ShipmentStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class ShipmentItemDetailDto {

    private final Long id;
    private final BigDecimal quantity;
    private final BigDecimal unitPrice;
    private final BigDecimal amount;
    private final BigDecimal stock;
    private final ShipmentInfo shipment;
    private final ItemInfo item;
    private final LotInfo lot;

    @QueryProjection
    public ShipmentItemDetailDto(Long id,
                                 BigDecimal quantity,
                                 BigDecimal unitPrice,
                                 BigDecimal amount,
                                 BigDecimal stock,
                                 Long shipmentId,
                                 String shipmentNo,
                                 LocalDate shipmentDate,
                                 ShipmentStatus shipmentStatus,
                                 Long partnerId,
                                 String partnerName,
                                 Long itemId,
                                 String itemCode,
                                 String itemName,
                                 ItemType itemType,
                                 String itemSpec,
                                 Long itemUomId,
                                 String itemUomCode,
                                 Integer itemUomScale,
                                 Long lotId,
                                 String lotNo) {
        this.id = id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.stock = stock;
        this.shipment = new ShipmentInfo(
                shipmentId,
                shipmentNo,
                shipmentDate,
                shipmentStatus,
                new PartnerInfo(partnerId, partnerName)
        );
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemType,
                itemSpec,
                new ItemUomInfo(itemUomId, itemUomCode, itemUomScale));
        this.lot = new LotInfo(
                lotId,
                lotNo
        );
    }

    public record ShipmentInfo(Long id,
                               String shipmentNo,
                               LocalDate date,
                               ShipmentStatus status,
                               PartnerInfo partner) {
    }

    public record PartnerInfo(Long id, String name) {
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

    public record LotInfo(Long id, String no) {
    }
}

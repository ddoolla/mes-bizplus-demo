package com.bizplus.mes.domain.shipment.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ShipmentItemUpdateDto {

    private Long id;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
}

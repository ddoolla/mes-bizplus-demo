package com.bizplus.mes.domain.shipment.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShipmentItemCreateDto {

    private Long itemId;
    private List<Long> inventoryIds;
}

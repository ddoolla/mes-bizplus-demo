package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDto;

import java.util.List;

public interface ShipmentItemService {

    List<ShipmentItemDto> getAll(Long shipmentId);
}

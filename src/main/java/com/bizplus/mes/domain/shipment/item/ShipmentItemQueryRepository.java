package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDto;

import java.util.List;

public interface ShipmentItemQueryRepository {

    List<ShipmentItemDto> fetchShipmentItems(Long shipmentId);
}

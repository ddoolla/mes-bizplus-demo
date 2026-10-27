package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDto;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemUpdateDto;

import java.util.List;

public interface ShipmentItemService {

    List<ShipmentItemDto> getAll(Long shipmentId);

    void updateAll(List<ShipmentItemUpdateDto> dtoList);
}

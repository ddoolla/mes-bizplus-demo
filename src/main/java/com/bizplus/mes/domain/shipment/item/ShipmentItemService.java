package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemCreateDto;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDto;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemUpdateDto;

import java.util.List;

public interface ShipmentItemService {

    List<ShipmentItemDto> getAll(Long shipmentId);

    void createAll(Long shipmentId, ShipmentItemCreateDto dto);

    void updateAll(List<ShipmentItemUpdateDto> dtoList);

    void deleteAll(List<Long> ids);
}

package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDetailDto;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDto;

import java.util.List;

public interface ShipmentItemQueryRepository {

    List<ShipmentItemDto> fetchShipmentItems(Long shipmentId);

    List<ShipmentItemDetailDto> fetchAllByLotId(Long lotId);

    List<ShipmentItemDetailDto> fetchAllInLotId(List<Long> lotIds);

    boolean existsShipmentItem(Long shipmentId, Long itemId, Long lotId);
}

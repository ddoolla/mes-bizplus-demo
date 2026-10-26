package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentItemServiceImpl implements ShipmentItemService {

    private final ShipmentItemQueryRepository shipmentItemQueryRepository;

    @Override
    public List<ShipmentItemDto> getAll(Long shipmentId) {
        return shipmentItemQueryRepository.fetchShipmentItems(shipmentId);
    }
}

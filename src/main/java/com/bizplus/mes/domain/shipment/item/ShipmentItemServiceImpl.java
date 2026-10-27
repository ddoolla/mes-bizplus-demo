package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDto;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentItemServiceImpl implements ShipmentItemService {

    private final ShipmentItemRepository shipmentItemRepository;

    private final ShipmentItemReader shipmentItemReader;

    @Override
    public List<ShipmentItemDto> getAll(Long shipmentId) {
        return shipmentItemRepository.fetchShipmentItems(shipmentId);
    }

    @Transactional
    @Override
    public void updateAll(List<ShipmentItemUpdateDto> dtoList) {
        if (dtoList.isEmpty()) {
            return;
        }

        dtoList.forEach(dto -> {
            ShipmentItem shipmentItem = shipmentItemReader.getById(dto.getId());

            BigDecimal amount = dto.getQuantity()
                    .multiply(dto.getUnitPrice());

            shipmentItem.update(
                    dto.getQuantity(),
                    dto.getUnitPrice(),
                    amount
            );
        });
    }
}

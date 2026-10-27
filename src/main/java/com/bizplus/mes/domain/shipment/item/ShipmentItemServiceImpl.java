package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.domain.inventory.Inventory;
import com.bizplus.mes.domain.inventory.InventoryReader;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.shipment.Shipment;
import com.bizplus.mes.domain.shipment.ShipmentReader;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemCreateDto;
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

    private final ItemReader itemReader;
    private final InventoryReader inventoryReader;
    private final ShipmentReader shipmentReader;
    private final ShipmentItemReader shipmentItemReader;

    @Override
    public List<ShipmentItemDto> getAll(Long shipmentId) {
        return shipmentItemRepository.fetchShipmentItems(shipmentId);
    }

    @Transactional
    @Override
    public void createAll(Long shipmentId, ShipmentItemCreateDto dto) {
        Shipment shipment = shipmentReader.getById(shipmentId);
        Item item = itemReader.getById(dto.getItemId());

        if (!item.isLotManaged()) {
            boolean exists = shipmentItemRepository.existsShipmentItem(shipmentId, item.getId(), null);

            if (!exists) {
                Inventory inventory = inventoryReader.get(item.getId(), null);

                BigDecimal amount = null;

                if (item.getUnitPrice() != null && inventory.getQuantity() != null) {
                    amount = inventory.getQuantity().multiply(item.getUnitPrice());
                }

                shipmentItemRepository.save(new ShipmentItem(
                        shipment,
                        item,
                        null,
                        inventory.getQuantity(),
                        item.getUnitPrice(),
                        amount
                ));
            }

        } else {
            dto.getInventoryIds().forEach(inventoryId -> {
                Inventory inventory = inventoryReader.getById(inventoryId);
                Lot lot = inventory.getLot();


                boolean exists = shipmentItemRepository
                        .existsShipmentItem(shipmentId, item.getId(), lot.getId());

                if (!exists) {
                    BigDecimal amount = null;

                    if (item.getUnitPrice() != null && inventory.getQuantity() != null) {
                        amount = inventory.getQuantity().multiply(item.getUnitPrice());
                    }

                    shipmentItemRepository.save(new ShipmentItem(
                            shipment,
                            item,
                            lot,
                            inventory.getQuantity(),
                            item.getUnitPrice(),
                            amount
                    ));
                }
            });
        }
    }

    @Transactional
    @Override
    public void updateAll(List<ShipmentItemUpdateDto> dtoList) {
        if (dtoList.isEmpty()) {
            return;
        }

        dtoList.forEach(dto -> {
            ShipmentItem shipmentItem = shipmentItemReader.getById(dto.getId());

            BigDecimal quantity = dto.getQuantity() != null ? dto.getQuantity() : BigDecimal.ZERO;
            BigDecimal unitPrice = dto.getUnitPrice() != null ? dto.getUnitPrice() : BigDecimal.ZERO;

            BigDecimal amount = quantity.multiply(unitPrice);

            shipmentItem.update(
                    dto.getQuantity(),
                    dto.getUnitPrice(),
                    amount
            );
        });
    }
}

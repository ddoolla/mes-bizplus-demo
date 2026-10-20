package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryReader {

    private final InventoryRepository inventoryRepository;

    public Inventory get(Long itemId, Long lotId) {
        return inventoryRepository.findInventory(itemId, lotId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INVENTORY_NOT_FOUND,
                        String.format("itemId: %d, lotId: %d", itemId, lotId)));
    }

    public Inventory getForUpdate(Long itemId, Long lotId) {
        return inventoryRepository.findInventoryForUpdate(itemId, lotId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INVENTORY_NOT_FOUND,
                        String.format("itemId: %d, lotId: %d", itemId, lotId)));

    }

    public boolean hasStock(Long itemId) {
        return inventoryRepository.existsStockByItemId(itemId);
    }
}

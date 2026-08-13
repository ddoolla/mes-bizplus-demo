package com.bizplus.mes.domain.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryReader {

    private final InventoryRepository inventoryRepository;

    public boolean hasStock(Long itemId) {
        return inventoryRepository.existsStockByItemId(itemId);
    }
}

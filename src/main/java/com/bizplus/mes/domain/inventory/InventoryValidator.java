package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryValidator {

    private final InventoryRepository inventoryRepository;

    public void validateNoStock(Long itemId) {

        if (inventoryRepository.existsStockByItemId(itemId)) {
            throw new BusinessException(ErrorCode.ITEM_HAS_STOCK, "itemId: " + itemId);
        }
    }

}

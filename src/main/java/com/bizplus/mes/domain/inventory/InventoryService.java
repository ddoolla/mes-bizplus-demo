package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.inventory.dto.ItemInventoryListDto;
import com.bizplus.mes.domain.inventory.dto.ItemInventorySearchDto;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    ItemInventoryListDto getItemInventories(ItemInventorySearchDto dto, Pageable pageable);

    void createInventory(Long itemId);
}

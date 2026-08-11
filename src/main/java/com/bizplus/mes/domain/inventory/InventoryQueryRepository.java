package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.inventory.dto.ItemInventoryDto;
import com.bizplus.mes.domain.inventory.dto.ItemInventorySearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryQueryRepository {

    Page<ItemInventoryDto> findInventories(ItemInventorySearchDto dto, Pageable pageable);
}

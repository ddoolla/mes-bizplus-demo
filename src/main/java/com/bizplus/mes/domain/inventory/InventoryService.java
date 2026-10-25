package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.inventory.dto.InventoryListDto;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    InventoryListDto getInventories(InventorySearchDto dto, Pageable pageable);
}

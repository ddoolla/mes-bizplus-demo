package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.inventory.dto.InventoryDto;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryQueryRepository {

    Page<InventoryDto> findInventoriesGroupByItem(InventorySearchDto dto, Pageable pageable);
}

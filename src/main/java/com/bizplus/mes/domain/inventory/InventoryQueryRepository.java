package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.inventory.dto.InventoryDto;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import com.bizplus.mes.domain.inventory.dto.InventorySummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InventoryQueryRepository {

    Page<InventorySummaryDto> fetchInventorySummaries(InventorySearchDto dto, Pageable pageable);

    List<InventoryDto> fetchInventories(Long itemId);

    Optional<Inventory> findInventory(Long itemId, Long lotId);

    Optional<Inventory> findInventoryForUpdate(Long itemId, Long lotId);

    List<Inventory> findAvailableInventories(Long itemId);
}

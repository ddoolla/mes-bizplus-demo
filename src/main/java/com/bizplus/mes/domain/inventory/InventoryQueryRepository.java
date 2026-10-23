package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import com.bizplus.mes.domain.inventory.dto.InventorySummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InventoryQueryRepository {

    Page<InventorySummaryDto> findInventorySummaries(InventorySearchDto dto, Pageable pageable);

    Optional<Inventory> findInventory(Long itemId, Long lotId);

    Optional<Inventory> findInventoryForUpdate(Long itemId, Long lotId);

    boolean existsStockByItemId(Long itemId);

    List<Inventory> findAvailableInventories(Long itemId);
}

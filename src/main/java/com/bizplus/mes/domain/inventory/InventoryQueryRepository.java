package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.inventory.dto.InventoryDto;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InventoryQueryRepository {

    Page<InventoryDto> findInventoriesGroupByItem(InventorySearchDto dto, Pageable pageable);

    Optional<Inventory> findInventoryForUpdate(Long itemId, Long lotId);

    boolean existsStockByItemId(Long itemId);

    Optional<Inventory> findByItemIdAndLotIsNull(Long itemId);
}

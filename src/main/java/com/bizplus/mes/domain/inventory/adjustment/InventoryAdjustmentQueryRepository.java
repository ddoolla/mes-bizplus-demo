package com.bizplus.mes.domain.inventory.adjustment;

import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentDto;
import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryAdjustmentQueryRepository {

    Page<InventoryAdjustmentDto> fetchAll(InventoryAdjustmentSearchDto dto, Pageable pageable);
}

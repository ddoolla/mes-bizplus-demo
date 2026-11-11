package com.bizplus.mes.domain.inventory.adjustment;

import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentCreateDto;
import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentListDto;
import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentSearchDto;
import org.springframework.data.domain.Pageable;

public interface InventoryAdjustmentService {

    InventoryAdjustmentListDto getAll(InventoryAdjustmentSearchDto dto, Pageable pageable);

    void create(InventoryAdjustmentCreateDto dto);
}

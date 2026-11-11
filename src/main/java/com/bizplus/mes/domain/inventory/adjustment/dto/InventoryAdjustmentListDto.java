package com.bizplus.mes.domain.inventory.adjustment.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InventoryAdjustmentListDto {

    private List<InventoryAdjustmentDto> inventoryAdjustments;
    private Pagination pagination;
}

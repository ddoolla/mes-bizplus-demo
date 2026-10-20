package com.bizplus.mes.domain.inventory.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InventoryListDto {

    private List<InventorySummaryDto> inventories;
    private Pagination pagination;
}

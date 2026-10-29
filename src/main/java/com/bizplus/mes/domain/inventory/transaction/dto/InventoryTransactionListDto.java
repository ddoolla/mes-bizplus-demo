package com.bizplus.mes.domain.inventory.transaction.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InventoryTransactionListDto {

    private List<InventoryTransactionDto> inventoryTransactions;
    private Pagination pagination;
}

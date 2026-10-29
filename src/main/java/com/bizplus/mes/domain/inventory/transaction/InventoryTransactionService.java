package com.bizplus.mes.domain.inventory.transaction;

import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionListDto;
import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionSearchDto;
import org.springframework.data.domain.Pageable;

public interface InventoryTransactionService {

    InventoryTransactionListDto getAll(InventoryTransactionSearchDto dto, Pageable pageable);
}

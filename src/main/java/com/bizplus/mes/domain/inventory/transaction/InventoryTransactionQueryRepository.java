package com.bizplus.mes.domain.inventory.transaction;

import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionDto;
import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryTransactionQueryRepository {

    Page<InventoryTransactionDto> fetchAll(InventoryTransactionSearchDto dto, Pageable pageable);
}

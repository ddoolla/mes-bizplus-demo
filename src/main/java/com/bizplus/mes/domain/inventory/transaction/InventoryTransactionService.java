package com.bizplus.mes.domain.inventory.transaction;

import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionDto;
import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionListDto;
import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionSearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryTransactionService {

    InventoryTransactionListDto getAll(InventoryTransactionSearchDto dto, Pageable pageable);

    List<InventoryTransactionDto> getAllByLotId(Long lotId);
}

package com.bizplus.mes.domain.inventory.transaction;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionDto;
import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionListDto;
import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

    private final InventoryTransactionRepository inventoryTransactionRepository;

    @Override
    public InventoryTransactionListDto getAll(InventoryTransactionSearchDto dto, Pageable pageable) {
        Page<InventoryTransactionDto> page = inventoryTransactionRepository.fetchAll(dto, pageable);

        return new InventoryTransactionListDto(page.getContent(), Pagination.of(page));
    }
}

package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.inventory.dto.InventoryListDto;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import com.bizplus.mes.domain.inventory.dto.InventorySummaryDto;
import com.bizplus.mes.domain.inventory.transaction.InventoryTransactionRepository;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.lot.LotReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    private final ItemReader itemReader;
    private final LotReader lotReader;

    @Override
    public InventoryListDto getInventories(InventorySearchDto dto, Pageable pageable) {
        Page<InventorySummaryDto> inventoryPage = inventoryRepository.findInventorySummaries(dto, pageable);

        return new InventoryListDto(
                inventoryPage.getContent(),
                Pagination.of(inventoryPage));
    }
}

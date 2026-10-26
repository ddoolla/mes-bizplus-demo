package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.inventory.dto.InventoryDto;
import com.bizplus.mes.domain.inventory.dto.InventoryListDto;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import com.bizplus.mes.domain.inventory.dto.InventorySummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryListDto getAll(InventorySearchDto dto, Pageable pageable) {
        Page<InventorySummaryDto> inventoryPage = inventoryRepository.fetchInventorySummaries(dto, pageable);

        return new InventoryListDto(
                inventoryPage.getContent(),
                Pagination.of(inventoryPage));
    }

    @Override
    public List<InventoryDto> getAllByItemId(Long itemId) {
        return inventoryRepository.fetchInventories(itemId);
    }
}

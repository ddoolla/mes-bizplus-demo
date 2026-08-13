package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.inventory.dto.InventoryDto;
import com.bizplus.mes.domain.inventory.dto.InventoryListDto;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryListDto getInventories(InventorySearchDto dto, Pageable pageable) {
        Page<InventoryDto> inventoryPage = inventoryRepository.findInventoriesGroupByItem(dto, pageable);

        return new InventoryListDto(
                inventoryPage.getContent(),
                Pagination.of(inventoryPage));
    }
}

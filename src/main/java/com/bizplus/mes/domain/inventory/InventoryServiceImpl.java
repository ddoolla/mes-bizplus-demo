package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.inventory.dto.ItemInventoryDto;
import com.bizplus.mes.domain.inventory.dto.ItemInventoryListDto;
import com.bizplus.mes.domain.inventory.dto.ItemInventorySearchDto;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final ItemReader itemReader;

    @Override
    public ItemInventoryListDto getItemInventories(ItemInventorySearchDto dto, Pageable pageable) {

        Page<ItemInventoryDto> inventoryPage = inventoryRepository.findInventories(dto, pageable);

        return new ItemInventoryListDto(
                inventoryPage.getContent(),
                Pagination.of(inventoryPage));
    }

    @Override
    public void createInventory(Long itemId) {

        Item item = itemReader.getById(itemId);

        inventoryRepository.save(new Inventory(item, BigDecimal.ZERO, BigDecimal.ZERO));
    }
}

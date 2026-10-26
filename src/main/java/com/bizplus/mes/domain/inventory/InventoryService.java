package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.inventory.dto.InventoryDto;
import com.bizplus.mes.domain.inventory.dto.InventoryListDto;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryService {

    InventoryListDto getAll(InventorySearchDto dto, Pageable pageable);

    List<InventoryDto> getAllByItemId(Long itemId);
}

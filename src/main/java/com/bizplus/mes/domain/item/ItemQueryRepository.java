package com.bizplus.mes.domain.item;

import com.bizplus.mes.domain.item.dto.ItemDto;
import com.bizplus.mes.domain.item.dto.ItemExcelDto;
import com.bizplus.mes.domain.item.dto.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ItemQueryRepository {

    Page<ItemDto> fetchItems(ItemSearchDto dto, Pageable pageable);

    List<ItemExcelDto> fetchForExcel(ItemSearchDto dto);

    Optional<ItemDto> fetchItem(Long id);

    Optional<Item> findItemForUpdate(Long id);
}

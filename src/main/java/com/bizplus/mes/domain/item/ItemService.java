package com.bizplus.mes.domain.item;

import com.bizplus.mes.domain.item.dto.ItemCreateDto;
import com.bizplus.mes.domain.item.dto.ItemDto;
import com.bizplus.mes.domain.item.dto.ItemListDto;
import com.bizplus.mes.domain.item.dto.ItemSearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    ItemListDto getItems(ItemSearchDto dto, Pageable pageable);

    ItemDto getItem(Long id);

    boolean checkCode(Long id, String code);

    Long createItem(ItemCreateDto dto);

    void deleteItems(List<Long> ids);
}

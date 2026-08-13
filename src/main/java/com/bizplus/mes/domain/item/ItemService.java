package com.bizplus.mes.domain.item;

import com.bizplus.mes.domain.item.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    ItemListDto getItems(ItemSearchDto dto, Pageable pageable);

    ItemDto getItem(Long id);

    boolean checkCode(Long id, String code);

    void createItem(ItemCreateDto dto);

    void updateItem(Long id, ItemUpdateDto dto);

    void deleteItems(List<Long> ids);
}

package com.bizplus.mes.domain.item;

import com.bizplus.mes.domain.item.dto.ItemDto;
import com.bizplus.mes.domain.item.dto.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemQueryRepository {

    Page<ItemDto> findItems(ItemSearchDto dto, Pageable pageable);

    Page<ItemDto> findProducts(ItemSearchDto dto, Pageable pageable);

    Page<ItemDto> findMaterials(ItemSearchDto dto, Pageable pageable);

    Page<ItemDto> findBomItems(ItemSearchDto dto, Pageable pageable);

    Optional<ItemDto> findItem(Long id);
}

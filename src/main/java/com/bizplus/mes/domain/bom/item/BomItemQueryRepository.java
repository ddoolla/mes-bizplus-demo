package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.domain.bom.item.dto.BomItemDto;

import java.util.List;
import java.util.Optional;

public interface BomItemQueryRepository {

    List<BomItemDto> findBomItems(Long bomId);

    Optional<BomItem> findIncludingDeleted(Long bomId, Long itemId);

    List<Long> findComponentItemIds(Long parentItemId);
}

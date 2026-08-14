package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.domain.bom.item.dto.BomItemDto;

import java.util.List;

public interface BomItemQueryRepository {

    List<BomItemDto> findBomItems(Long bomId);
}

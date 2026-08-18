package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.domain.bom.item.dto.BomItemCreateDto;
import com.bizplus.mes.domain.bom.item.dto.BomItemDto;
import com.bizplus.mes.domain.bom.item.dto.BomItemUpdateDto;

import java.util.List;

public interface BomItemService {

    List<BomItemDto> getBomItems(Long bomId);

    void createBomItems(Long bomId, BomItemCreateDto dto);

    void updateBomItems(List<BomItemUpdateDto> dtos);

    void deleteBomItems(List<Long> ids);
}

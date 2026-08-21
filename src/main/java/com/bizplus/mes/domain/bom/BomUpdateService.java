package com.bizplus.mes.domain.bom;

import com.bizplus.mes.domain.bom.dto.BomUpdateDto;
import com.bizplus.mes.domain.bom.item.BomItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BomUpdateService {

    private final BomService bomService;
    private final BomItemService bomItemService;

    @Transactional
    public void update(Long bomId, BomUpdateDto dto) {

        bomService.updateBom(bomId, dto);
        bomItemService.updateBomItems(dto.getBomItems());
    }
}

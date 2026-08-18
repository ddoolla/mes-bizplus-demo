package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BomItemReader {

    private final BomItemRepository bomItemRepository;

    public BomItem getById(Long id) {
        return bomItemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOM_ITEM_NOT_FOUND, "id: " + id));
    }
}

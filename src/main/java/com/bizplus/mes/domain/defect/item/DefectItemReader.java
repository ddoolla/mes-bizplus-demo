package com.bizplus.mes.domain.defect.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefectItemReader {

    private final DefectItemRepository defectItemRepository;

    public DefectItem getById(Long id) {
        return defectItemRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.DEFECT_ITEM_NOT_FOUND, "id: " + id));
    }
}

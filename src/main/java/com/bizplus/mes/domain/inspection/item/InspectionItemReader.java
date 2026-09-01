package com.bizplus.mes.domain.inspection.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InspectionItemReader {

    private final InspectionItemRepository inspectionItemRepository;

    public InspectionItem getById(Long id) {
        return inspectionItemRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INSPECTION_ITEM_NOT_FOUND, "id: " + id));
    }
}

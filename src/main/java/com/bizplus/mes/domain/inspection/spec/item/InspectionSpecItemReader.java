package com.bizplus.mes.domain.inspection.spec.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InspectionSpecItemReader {

    private final InspectionSpecItemRepository inspectionSpecItemRepository;

    public InspectionSpecItem getById(Long id) {
        return inspectionSpecItemRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INSPECTION_SPEC_ITEM_NOT_FOUND, "id: " + id));
    }

    public List<InspectionSpecItem> getAll(Long inspectionSpecId) {
        return inspectionSpecItemRepository.findInspectionSpecItems(inspectionSpecId);
    }
}

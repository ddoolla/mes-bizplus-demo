package com.bizplus.mes.domain.inspection.spec;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InspectionSpecReader {

    private final InspectionSpecRepository inspectionSpecRepository;

    public InspectionSpec getById(Long id) {
        return inspectionSpecRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INSPECTION_SPEC_NOT_FOUND, "id: " + id));
    }

    public InspectionSpec getByIdOrNull(Long id) {
        return id == null
                ? null
                : inspectionSpecRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INSPECTION_SPEC_NOT_FOUND, "id: " + id));
    }
}

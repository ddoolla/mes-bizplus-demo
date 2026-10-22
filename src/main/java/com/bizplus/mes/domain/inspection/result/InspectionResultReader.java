package com.bizplus.mes.domain.inspection.result;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InspectionResultReader {

    private final InspectionResultRepository inspectionResultRepository;

    public InspectionResult getById(Long id) {
        return inspectionResultRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INSPECTION_RESULT_NOT_FOUND,
                        String.format("id: %d", id)
                ));
    }
}

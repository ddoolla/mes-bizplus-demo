package com.bizplus.mes.domain.inspection;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InspectionReader {

    private final InspectionRepository inspectionRepository;

    public Inspection getById(Long id) {
        return inspectionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INSPECTION_NOT_FOUND,
                        String.format("id: %d", id)
                ));
    }
}

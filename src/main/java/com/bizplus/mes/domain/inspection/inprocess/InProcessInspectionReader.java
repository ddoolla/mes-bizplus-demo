package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InProcessInspectionReader {

    private final InProcessInspectionRepository inProcessInspectionRepository;

    public InProcessInspection getById(Long id) {
        return inProcessInspectionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INSPECTION_NOT_FOUND,
                        "in process inspection id: %d".formatted(id)
                ));
    }
}

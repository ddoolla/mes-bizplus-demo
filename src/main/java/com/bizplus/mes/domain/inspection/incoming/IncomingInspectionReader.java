package com.bizplus.mes.domain.inspection.incoming;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncomingInspectionReader {

    private final IncomingInspectionRepository incomingInspectionRepository;

    public IncomingInspection getById(Long id) {
        return incomingInspectionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INSPECTION_NOT_FOUND,
                        "incoming inspection id: %d".formatted(id)
                ));
    }
}

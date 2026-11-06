package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.work.result.dto.WorkResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkResultReader {

    private final WorkResultRepository workResultRepository;

    public WorkResult getById(Long id) {
        return workResultRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.WORK_RESULT_NOT_FOUND,
                        "WorkResult id: %d".formatted(id)
                ));
    }

    public WorkResult getByWorkOrderId(Long workOrderId) {
        return workResultRepository.findByWorkOrderId(workOrderId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.WORK_RESULT_NOT_FOUND,
                        "WorkOrder id: %d".formatted(workOrderId)
                ));
    }

    public WorkResultDto getDtoById(Long id) {
        return workResultRepository.fetchById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.WORK_RESULT_NOT_FOUND,
                        "WorkResult id: %d".formatted(id)
                ));
    }
}

package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkResultReader {

    private final WorkResultRepository workResultRepository;

    public WorkResult getByWorkOrderId(Long workOrderId) {
        return workResultRepository.findByWorkOrderId(workOrderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORK_RESULT_NOT_FOUND, "workOrderId: " + workOrderId));
    }
}

package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkOrderReader {

    private final WorkOrderRepository workOrderRepository;

    public WorkOrder getById(Long id) {
        return workOrderRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORK_ORDER_NOT_FOUND, "id: " + id));
    }
}

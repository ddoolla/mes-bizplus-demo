package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.work.order.dto.WorkOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkOrderReader {

    private final WorkOrderRepository workOrderRepository;

    public WorkOrder getById(Long id) {
        return workOrderRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.WORK_ORDER_NOT_FOUND,
                        "work order id: %d".formatted(id)
                ));
    }

    public WorkOrder getForUpdate(Long id) {
        return workOrderRepository.findWorkOrderForUpdate(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.WORK_ORDER_NOT_FOUND,
                        "work order id: %d".formatted(id)
                ));
    }

    public WorkOrderDto getDtoById(Long id) {
        return workOrderRepository.fetchWorkOrder(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.WORK_ORDER_NOT_FOUND,
                        "work order id: %d".formatted(id)
                ));
    }
}

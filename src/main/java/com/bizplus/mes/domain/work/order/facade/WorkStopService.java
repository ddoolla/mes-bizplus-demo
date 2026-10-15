package com.bizplus.mes.domain.work.order.facade;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import com.bizplus.mes.domain.work.order.WorkOrderService;
import com.bizplus.mes.domain.work.order.WorkOrderStatus;
import com.bizplus.mes.domain.work.order.dto.WorkStopDto;
import com.bizplus.mes.domain.work.result.WorkResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkStopService {

    private final WorkOrderReader workOrderReader;

    private final WorkOrderService workOrderService;
    private final WorkResultService workResultService;

    @Transactional
    public void stop(Long workOrderId, WorkStopDto dto) {
        WorkOrder workOrder = workOrderReader.getForUpdate(workOrderId);

        if (workOrder.getStatus() != WorkOrderStatus.IN_PROGRESS) {
            throw new BusinessException(ErrorCode.INVALID_STATE, "작업 진행중 상태에서만 중지 가능합니다.");
        }

        // 작업지시 상태 업데이트
        workOrderService.stopWork(workOrderId);

        // 작업 실적 업데이트
        workResultService.stopWork(workOrderId, dto);
    }
}

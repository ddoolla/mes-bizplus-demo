package com.bizplus.mes.domain.work.order.facade;

import com.bizplus.mes.domain.work.order.WorkOrderService;
import com.bizplus.mes.domain.work.order.dto.WorkStopDto;
import com.bizplus.mes.domain.work.result.WorkResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkStopService {

    private final WorkOrderService workOrderService;
    private final WorkResultService workResultService;

    @Transactional
    public void stop(Long workOrderId, WorkStopDto dto) {
        // 작업지시 상태 업데이트
        workOrderService.stopWork(workOrderId);

        // 작업 실적 업데이트
        workResultService.stopWork(workOrderId, dto);
    }
}

package com.bizplus.mes.domain.work.order.facade;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.domain.equipment.Equipment;
import com.bizplus.mes.domain.production.order.ProductionOrderService;
import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import com.bizplus.mes.domain.work.order.WorkOrderService;
import com.bizplus.mes.domain.work.order.WorkOrderStatus;
import com.bizplus.mes.domain.work.order.event.WorkStartedEvent;
import com.bizplus.mes.domain.work.result.WorkResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkStartService {

    private final WorkOrderReader workOrderReader;

    private final ProductionOrderService productionOrderService;
    private final WorkOrderService workOrderService;
    private final WorkResultService workResultService;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void start(Long workOrderId) {
        // 1. 작업지시 잠금 조회
        WorkOrder workOrder = workOrderReader.getForUpdate(workOrderId);

        // 2. 작업 시작 가능 여부 검증
        if (workOrder.getStatus() != WorkOrderStatus.PENDING) {
            throw new BusinessException("작업 대기 상태에서만 시작 가능합니다.");
        }

        // 3. 작업지시 시작 처리
        Equipment equipment = workOrderService.startWork(workOrderId);

        // 4. 작업실적 생성
        workResultService.createWorkResult(workOrderId);

        // 5. 생산지시 상태 변경 - 생산중
        productionOrderService.startProduction(
                workOrder.getProductionOrderProcess().getProductionOrder().getId());

        // 6. PLC 초기화 이벤트 (senderId를 잘 맞춰줘야 함)
        if (equipment != null) {
            eventPublisher.publishEvent(new WorkStartedEvent(equipment.getSenderId()));
        }
    }
}

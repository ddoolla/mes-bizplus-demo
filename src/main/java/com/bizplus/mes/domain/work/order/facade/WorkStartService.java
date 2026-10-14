package com.bizplus.mes.domain.work.order.facade;

import com.bizplus.mes.domain.equipment.Equipment;
import com.bizplus.mes.domain.work.order.WorkOrderService;
import com.bizplus.mes.domain.work.order.event.WorkStartedEvent;
import com.bizplus.mes.domain.work.result.WorkResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkStartService {

    private final WorkOrderService workOrderService;
    private final WorkResultService workResultService;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void start(Long workOrderId) {
        // 작업지시 상태 변경
        Equipment equipment = workOrderService.startWork(workOrderId);

        // 작업실적 데이터 생성
        workResultService.createWorkResult(workOrderId);

        // 설비를 사용한 작업일 경우 - plc 초기화 이벤트 (senderId를 잘 맞춰줘야 함)
        if (equipment != null) {
            eventPublisher.publishEvent(new WorkStartedEvent(equipment.getSenderId()));
        }
    }
}

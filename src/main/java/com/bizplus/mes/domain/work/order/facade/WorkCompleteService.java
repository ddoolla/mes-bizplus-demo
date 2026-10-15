package com.bizplus.mes.domain.work.order.facade;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.inventory.InventoryService;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import com.bizplus.mes.domain.work.order.WorkOrderService;
import com.bizplus.mes.domain.work.order.WorkOrderStatus;
import com.bizplus.mes.domain.work.order.dto.WorkCompleteDto;
import com.bizplus.mes.domain.work.result.WorkResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WorkCompleteService {

    private final WorkOrderReader workOrderReader;

    private final WorkOrderService workOrderService;
    private final WorkResultService workResultService;
    private final InventoryService inventoryService;

    @Transactional
    public void complete(Long workOrderId, WorkCompleteDto dto) {
        // 1. 작업지시 잠금 조회
        WorkOrder workOrder = workOrderReader.getForUpdate(workOrderId);

        // 2. 완료 가능 여부 검증
        if (workOrder.getStatus() != WorkOrderStatus.IN_PROGRESS) {
            throw new BusinessException(ErrorCode.INVALID_STATE, "작업 진행중 상태에서만 완료 가능합니다.");
        }

        // 3. 작업실적 업데이트
        workResultService.completeWork(workOrderId, dto);

        // 4. 재고 증가
        ProductionOrder productionOrder = workOrder
                .getProductionOrderProcess()
                .getProductionOrder();

        inventoryService.changeStock(new InventoryChangeDto(
                productionOrder.getItem().getId(),
                productionOrder.getLot() != null
                        ? productionOrder.getLot().getId()
                        : null,
                TransactionType.PRODUCTION_IN,
                dto.getGoodQuantity(),
                LocalDateTime.now()
        ));

        // 5.작업지시 상태 업데이트
        workOrderService.completeWork(workOrderId);
    }
}

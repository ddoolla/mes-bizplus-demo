package com.bizplus.mes.domain.work.order.facade;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.domain.inventory.InventoryService;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.production.order.ProductionOrderReader;
import com.bizplus.mes.domain.production.order.ProductionOrderService;
import com.bizplus.mes.domain.production.order.process.ProductionOrderProcessService;
import com.bizplus.mes.domain.production.result.ProductionResultService;
import com.bizplus.mes.domain.production.result.dto.ProductionResultCreateDto;
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

    private final ProductionOrderReader productionOrderReader;
    private final WorkOrderReader workOrderReader;

    private final ProductionOrderService productionOrderService;
    private final ProductionOrderProcessService productionOrderProcessService;
    private final ProductionResultService productionResultService;
    private final WorkOrderService workOrderService;
    private final WorkResultService workResultService;
    private final InventoryService inventoryService;

    @Transactional
    public void complete(Long workOrderId, WorkCompleteDto dto) {
        // 0. 생산지시 잠금 조회 (여러 작업지시 동시 완료 방지)
        ProductionOrder productionOrder = productionOrderReader.getForUpdate(dto.getProductionOrderId());

        // 1. 작업지시 조회
        WorkOrder workOrder = workOrderReader.getById(workOrderId);

        // 2. 완료 가능 여부 검증
        if (workOrder.getStatus() != WorkOrderStatus.IN_PROGRESS) {
            throw new BusinessException("작업 진행중 상태에서만 완료 가능합니다.");
        }

        // 3. 작업실적 업데이트
        workResultService.completeWork(workOrderId, dto);

        // 4. 작업지시 상태 업데이트
        workOrderService.completeWork(workOrderId);

        // 마지막 공정일 경우
        Long processId = workOrder.getProductionOrderProcess().getId();
        boolean isLastProcess = productionOrderProcessService.isLastProcess(processId);

        if (isLastProcess) {
            LocalDateTime now = LocalDateTime.now();

            // 5. 재고 증가
            inventoryService.changeStock(new InventoryChangeDto(
                    productionOrder.getItem().getId(),
                    productionOrder.getLot() != null
                            ? productionOrder.getLot().getId()
                            : null,
                    TransactionType.PRODUCTION_IN,
                    dto.getGoodQuantity(),
                    now
            ));

            // 6. 생산실적 생성
            productionResultService.create(new ProductionResultCreateDto(
                    productionOrder.getId(),
                    workOrder.getId(),
                    dto.getProductionQuantity(),
                    dto.getGoodQuantity(),
                    dto.getDefectQuantity(),
                    now
            ));
        }

        // 모든 공정 작업 완료 시
        boolean isAllCompleted = workOrderService.isAllCompleted(productionOrder.getId());

        if (isAllCompleted) {
            // 7. 생산지시 상태 업데이트
            productionOrderService.completeProduction(productionOrder.getId());
        }
    }
}

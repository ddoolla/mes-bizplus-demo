package com.bizplus.mes.domain.work.order.facade;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.domain.inspection.inprocess.InProcessInspectionService;
import com.bizplus.mes.domain.inspection.result.InspectionResultService;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.inventory.InventoryManager;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.InventoryReferenceType;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.material.input.facade.MaterialInputAutoCreateService;
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
import com.bizplus.mes.domain.work.result.dto.WorkResultUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WorkCompleteService {

    private final ProductionOrderReader productionOrderReader;
    private final WorkOrderReader workOrderReader;
    private final InventoryManager inventoryManager;

    private final ProductionOrderService productionOrderService;
    private final ProductionOrderProcessService productionOrderProcessService;
    private final ProductionResultService productionResultService;
    private final WorkOrderService workOrderService;
    private final WorkResultService workResultService;
    private final MaterialInputAutoCreateService materialInputAutoCreateService;
    private final InProcessInspectionService inProcessInspectionService;
    private final InspectionResultService inspectionResultService;

    @Transactional
    public void complete(Long workOrderId, WorkCompleteDto dto) {
        // 0. 생산지시 잠금 조회 (여러 작업지시 동시 완료 방지)
        ProductionOrder productionOrder = productionOrderReader.getForUpdate(dto.getProductionOrderId());

        // 1. 작업지시 조회
        WorkOrder workOrder = workOrderReader.getById(workOrderId);

        // 2. 완료 가능 여부 검증
        if (workOrder.getStatus() != WorkOrderStatus.IN_PROGRESS
                && workOrder.getStatus() != WorkOrderStatus.PAUSED) {
            throw new BusinessException("작업 진행중 또는 중지 상태에서만 완료 가능합니다.");
        }

        LocalDateTime manufacturedDateTime = LocalDateTime.now();

        // 3. 작업실적 업데이트
        workResultService.update(
                workOrderId,
                new WorkResultUpdateDto(
                        dto.getProductionQuantity(),
                        dto.getGoodQuantity(),
                        dto.getDefectQuantity(),
                        manufacturedDateTime,
                        dto.getRemark()
                ));

        // 4. 작업지시 상태 업데이트
        workOrderService.completeWork(workOrderId);

        // 소모자재 자동 투입
        materialInputAutoCreateService.create(workOrderId, productionOrder.getQuantity());

        // 공정검사 자동 생성
        InspectionSpec defaultIpqcSpec = workOrder.getProductionOrderProcess().getRoutingProcess().getDefaultIpqcSpec();
        boolean inspectionRequired = workOrder.isProcessInspectionRequired();
        boolean inspectionAutoCreate = workOrder.isProcessInspectionAutoCreate();

        if (inspectionRequired && inspectionAutoCreate) {
            if (defaultIpqcSpec == null) {
                throw new BusinessException("기본 공정 검사기준이 설정되어 있지 않습니다.");
            }

            Long newInspectionId = inProcessInspectionService.autoCreate(
                    workOrderId,
                    defaultIpqcSpec.getId(),
                    manufacturedDateTime.toLocalDate()
            );

            inspectionResultService.createAll(
                    newInspectionId,
                    defaultIpqcSpec.getId()
            );
        }

        // 마지막 공정일 경우
        Long processId = workOrder.getProductionOrderProcess().getId();
        boolean isLastProcess = productionOrderProcessService.isLastProcess(processId);

        if (isLastProcess) {
            // 5. 재고 증가
            if (!inspectionRequired) { // 공정검사 진행 안하면 바로 재고처리
                inventoryManager.changeStock(new InventoryChangeDto(
                        productionOrder.getItem().getId(),
                        productionOrder.getLot() != null
                                ? productionOrder.getLot().getId()
                                : null,
                        TransactionType.PRODUCTION_IN,
                        dto.getGoodQuantity(),
                        manufacturedDateTime,
                        InventoryReferenceType.WORK_ORDER,
                        workOrderId
                ));
            }

            // 6. 생산실적 생성
            productionResultService.create(new ProductionResultCreateDto(
                    productionOrder.getId(),
                    workOrder.getId(),
                    dto.getProductionQuantity(),
                    dto.getGoodQuantity(),
                    dto.getDefectQuantity(),
                    manufacturedDateTime
            ));

            // 로트 있을 경우 제조일, 유효기간 업데이트
            Item item = productionOrder.getItem();
            Lot lot = productionOrder.getLot();

            if (lot != null) {
                LocalDate manufacturedDate = manufacturedDateTime.toLocalDate();
                LocalDate expirationDate = null;

                if (item.getShelfLife() != null && item.getShelfLifeUnit() != null) {
                    expirationDate = item.getShelfLifeUnit()
                            .calculateExpirationDate(manufacturedDate, item.getShelfLife());
                }

                lot.updateDates(manufacturedDate, expirationDate);
            }
        }

        // 모든 공정 작업 완료 시
        boolean isAllCompleted = workOrderService.isAllCompleted(productionOrder.getId());

        if (isAllCompleted) {
            // 7. 생산지시 상태 업데이트
            productionOrderService.completeProduction(productionOrder.getId());
        }
    }
}

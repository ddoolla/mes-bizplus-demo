package com.bizplus.mes.domain.inspection.inprocess.facade;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.inspection.InspectionJudgement;
import com.bizplus.mes.domain.inspection.InspectionStatus;
import com.bizplus.mes.domain.inspection.inprocess.InProcessInspection;
import com.bizplus.mes.domain.inspection.inprocess.InProcessInspectionReader;
import com.bizplus.mes.domain.inspection.result.InspectionResult;
import com.bizplus.mes.domain.inspection.result.InspectionResultReader;
import com.bizplus.mes.domain.inventory.InventoryManager;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.InventoryReferenceType;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.production.order.process.ProductionOrderProcessService;
import com.bizplus.mes.domain.work.result.WorkResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InProcessInspectionCompleteService {

    private final InProcessInspectionReader inProcessInspectionReader;
    private final InspectionResultReader inspectionResultReader;

    private final ProductionOrderProcessService productionOrderProcessService;

    private final InventoryManager inventoryManager;

    @Transactional
    public void complete(Long id) {
        InProcessInspection inProcessInspection = inProcessInspectionReader.getById(id);
        Inspection inspection = inProcessInspection.getInspection();
        WorkResult workResult = inProcessInspection.getWorkResult();

        if (inspection.getStatus() == InspectionStatus.COMPLETED) {
            throw new BusinessException("이미 완료된 검사입니다.");
        }

        // 판정 및 상태 업데이트
        List<InspectionResult> inspectionResults = inspectionResultReader.getAll(inspection.getId());

        if (inspectionResults.isEmpty()) {
            throw new BusinessException("등록된 검사 결과가 없습니다.");
        }

        boolean hasUnJudge = inspectionResults.stream()
                .anyMatch(result -> result.getJudgement() == null);

        if (hasUnJudge) {
            throw new BusinessException("모든 검사항목을 판정해 주세요.");
        }

        boolean allPass = inspectionResults.stream()
                .allMatch(result -> result.getJudgement() == InspectionJudgement.PASS);

        inspection.complete();
        inspection.updateJudgement(allPass
                ? InspectionJudgement.PASS
                : InspectionJudgement.FAIL
        );

        // 마지막 공정이면 재고처리
        boolean isLastProcess = productionOrderProcessService
                .isLastProcess(workResult.getWorkOrder().getProductionOrderProcess().getId());

        if (isLastProcess) {
            ProductionOrder productionOrder = workResult.getWorkOrder().getProductionOrderProcess().getProductionOrder();

            inventoryManager.changeStock(new InventoryChangeDto(
                    productionOrder.getItem().getId(),
                    productionOrder.getLot() != null
                            ? productionOrder.getLot().getId()
                            : null,
                    TransactionType.PRODUCTION_IN,
                    workResult.getGoodQuantity(),
                    LocalDateTime.now(),
                    InventoryReferenceType.WORK_ORDER,
                    workResult.getWorkOrder().getId()
            ));
        }
    }
}

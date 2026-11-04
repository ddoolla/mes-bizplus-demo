package com.bizplus.mes.domain.inspection.incoming.facade;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.inspection.InspectionJudgement;
import com.bizplus.mes.domain.inspection.InspectionStatus;
import com.bizplus.mes.domain.inspection.incoming.IncomingInspection;
import com.bizplus.mes.domain.inspection.incoming.IncomingInspectionReader;
import com.bizplus.mes.domain.inspection.result.InspectionResult;
import com.bizplus.mes.domain.inspection.result.InspectionResultReader;
import com.bizplus.mes.domain.inventory.InventoryManager;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.InventoryReferenceType;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.receipt.Receipt;
import com.bizplus.mes.domain.receipt.item.ReceiptItem;
import com.bizplus.mes.domain.receipt.item.ReceiptItemReader;
import com.bizplus.mes.domain.receipt.item.ReceiptItemStatus;
import com.bizplus.mes.domain.uom.conversion.UomConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomingInspectionCompleteService {

    private final IncomingInspectionReader incomingInspectionReader;
    private final InspectionResultReader inspectionResultReader;
    private final ReceiptItemReader receiptItemReader;

    private final InventoryManager inventoryManager;

    private final UomConversionService uomConversionService;

    @Transactional
    public void complete(Long id) {
        IncomingInspection incomingInspection = incomingInspectionReader.getById(id);
        Inspection inspection = incomingInspection.getInspection();
        ReceiptItem receiptItem = incomingInspection.getReceiptItem();
        Receipt receipt = receiptItem.getReceipt();

        if (inspection.getStatus() == InspectionStatus.COMPLETED) {
            throw new BusinessException("이미 완료된 검사입니다.");
        }

        if (receiptItem.getStatus() != ReceiptItemStatus.PENDING
                && receiptItem.getStatus() != ReceiptItemStatus.ON_HOLD) {
            throw new BusinessException("입고 처리할 수 없는 자재입니다.");
        }

        // 판정 및 입고 자재 상태 업데이트
        List<InspectionResult> inspectionResults = inspectionResultReader.getAll(inspection.getId());

        if (inspectionResults.isEmpty()) {
            throw new BusinessException("등록된 검사 결과가 없습니다.");
        }

        boolean hasUnJudge = inspectionResults.stream()
                .anyMatch(result -> result.getJudgement() == null);

        if (hasUnJudge) {
            throw new BusinessException("모든 검사항목을 판정해 주세요.");
        }

        // 검사 상태 업데이트
        inspection.complete();

        boolean allPass = inspectionResults.stream()
                .allMatch(result -> result.getJudgement() == InspectionJudgement.PASS);

        if (allPass) {
            inspection.updateJudgement(InspectionJudgement.PASS);

            // 입고 수량 단위 환산
            BigDecimal convertedQuantity = uomConversionService.convert(
                    receiptItem.getQuantity(),
                    receiptItem.getUom().getId(),
                    receiptItem.getItem().getUom().getId()
            );

            // 재고처리
            inventoryManager.changeStock(new InventoryChangeDto(
                    receiptItem.getItem().getId(),
                    receiptItem.getLot() != null
                            ? receiptItem.getLot().getId()
                            : null,
                    TransactionType.RECEIPT,
                    convertedQuantity,
                    LocalDateTime.now(),
                    InventoryReferenceType.RECEIPT,
                    receipt.getId()
            ));

            // 입고 자재 완료
            receiptItem.updateStatus(ReceiptItemStatus.COMPLETED);

        } else {
            inspection.updateJudgement(InspectionJudgement.FAIL);

            // 입고 보류
            receiptItem.updateStatus(ReceiptItemStatus.ON_HOLD);
        }

        // 전체 입고 완료 여부 확인
        List<ReceiptItem> receiptItems = receiptItemReader.getAll(receipt.getId());

        boolean allCompleted = receiptItems.stream()
                .allMatch(ri -> ri.getStatus() == ReceiptItemStatus.COMPLETED
                        || ri.getStatus() == ReceiptItemStatus.RETURNED);

        if (allCompleted) {
            receipt.complete();
        }
    }
}

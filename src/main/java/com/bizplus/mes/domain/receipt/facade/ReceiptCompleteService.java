package com.bizplus.mes.domain.receipt.facade;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.domain.inspection.incoming.IncomingInspectionService;
import com.bizplus.mes.domain.inspection.result.InspectionResultService;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.inventory.InventoryService;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.lot.LotNoGenerator;
import com.bizplus.mes.domain.lot.LotRepository;
import com.bizplus.mes.domain.receipt.Receipt;
import com.bizplus.mes.domain.receipt.ReceiptReader;
import com.bizplus.mes.domain.receipt.ReceiptStatus;
import com.bizplus.mes.domain.receipt.item.ReceiptItem;
import com.bizplus.mes.domain.receipt.item.ReceiptItemReader;
import com.bizplus.mes.domain.uom.conversion.UomConversionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptCompleteService {

    private final LotRepository lotRepository;

    private final LotNoGenerator lotNoGenerator;
    private final ReceiptReader receiptReader;
    private final ReceiptItemReader receiptItemReader;

    private final UomConversionService uomConversionService;
    private final InventoryService inventoryService;
    private final IncomingInspectionService incomingInspectionService;
    private final InspectionResultService inspectionResultService;

    @Transactional
    public void complete(Long receiptId) {
        Receipt receipt = receiptReader.getById(receiptId);
        LocalDate receiptDate = receipt.getDate();

        if (receipt.getStatus() != ReceiptStatus.DRAFT) {
            throw new BusinessException("작성중 상태에서만 완료처리 가능합니다.");
        }

        List<ReceiptItem> receiptItems = receiptItemReader.getAll(receiptId);

        if (receiptItems.isEmpty()) {
            throw new BusinessException("입고 자재가 등록되지 않아 완료할 수 없습니다.");
        }

        receiptItems.forEach(receiptItem -> {
            if (receiptItem.getQuantity() == null
                    ||receiptItem.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BusinessException("입고 수량을 확인해 주세요.");
            }

            Item item = receiptItem.getItem();
            InspectionSpec defaultIqcSpec = item.getDefaultIqcSpec();

            Lot lot = null;

            // 로트 생성 및 업데이트
            if (item.isLotManaged()) {
                lot = lotRepository.save(new Lot(
                        item,
                        lotNoGenerator.generate(
                                "LOT",
                                receiptDate
                        )
                ));

                receiptItem.updateLot(lot);
            }

            // 수입검사 데이터 자동생성
            if (defaultIqcSpec != null) {
                // 수입검사 데이터
                Long newInspectionId = incomingInspectionService.autoCreate(
                        receiptItem.getId(),
                        defaultIqcSpec.getId(),
                        receiptDate
                );

                // 검사 결과 데이터
                inspectionResultService.createAll(
                        newInspectionId,
                        defaultIqcSpec.getId()
                );
            }

            // 입고 수량 단위 환산
            BigDecimal convertedQuantity = uomConversionService.convert(
                    receiptItem.getQuantity(),
                    receiptItem.getUom().getId(),
                    item.getUom().getId()
            );

            // 재고 처리
            inventoryService.changeStock(new InventoryChangeDto(
                    item.getId(),
                    lot != null ? lot.getId() : null,
                    TransactionType.RECEIPT,
                    convertedQuantity,
                    receipt.getDate().atStartOfDay()
            ));
        });

        // 입고 완료상태 업데이트
        receipt.complete();
    }
}

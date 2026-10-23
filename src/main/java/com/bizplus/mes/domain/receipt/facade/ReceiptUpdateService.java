package com.bizplus.mes.domain.receipt.facade;

import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.partner.PartnerReader;
import com.bizplus.mes.domain.receipt.Receipt;
import com.bizplus.mes.domain.receipt.ReceiptReader;
import com.bizplus.mes.domain.receipt.ReceiptStatus;
import com.bizplus.mes.domain.receipt.dto.ReceiptUpdateDto;
import com.bizplus.mes.domain.receipt.item.ReceiptItem;
import com.bizplus.mes.domain.receipt.item.ReceiptItemReader;
import com.bizplus.mes.domain.receipt.item.ReceiptItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceiptUpdateService {

    private final PartnerReader partnerReader;
    private final ReceiptReader receiptReader;
    private final ReceiptItemReader receiptItemReader;

    private final ReceiptItemService receiptItemService;

    @Transactional
    public void update(Long receiptId, ReceiptUpdateDto dto) {
        Receipt receipt = receiptReader.getById(receiptId);

        if (receipt.getStatus() == ReceiptStatus.DRAFT) {
            Partner partner = partnerReader.getById(dto.getPartnerId());

            // 1. 입고 정보 수정
            receipt.update(
                    partner,
                    dto.getDate(),
                    dto.getRemark()
            );

            // 2. 입고 자재 정보 수정
            receiptItemService.update(dto.getReceiptItems());

        } else if (receipt.getStatus() == ReceiptStatus.COMPLETED) {
            // 입고 완료 시 비고만 수정 가능
            receipt.updateRemark(dto.getRemark());

            dto.getReceiptItems().forEach(receiptItemDto -> {
                ReceiptItem receiptItem = receiptItemReader.getById(receiptItemDto.getId());
                receiptItem.updateRemark(receiptItemDto.getRemark());
            });
        }
    }
}

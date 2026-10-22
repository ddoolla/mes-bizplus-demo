package com.bizplus.mes.domain.receipt.facade;

import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.partner.PartnerReader;
import com.bizplus.mes.domain.receipt.Receipt;
import com.bizplus.mes.domain.receipt.ReceiptReader;
import com.bizplus.mes.domain.receipt.ReceiptStatus;
import com.bizplus.mes.domain.receipt.dto.ReceiptUpdateDto;
import com.bizplus.mes.domain.receipt.item.ReceiptItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceiptUpdateService {

    private final PartnerReader partnerReader;
    private final ReceiptReader receiptReader;

    private final ReceiptItemService receiptItemService;

    @Transactional
    public void update(Long receiptId, ReceiptUpdateDto dto) {
        Receipt receipt = receiptReader.getById(receiptId);
        Partner partner = partnerReader.getById(dto.getPartnerId());

        if (receipt.getStatus() != ReceiptStatus.DRAFT) {
            throw new IllegalStateException(
                    String.format("[입고 수정] 작성중 상태의 입고만 수정할 수 있습니다. (receiptId: %d)", receiptId));
        }

        // 1. 입고 정보 수정
        receipt.update(
                partner,
                dto.getDate(),
                dto.getRemark()
        );

        // 2. 입고 자재 정보 수정
        receiptItemService.update(dto.getReceiptItems());
    }
}

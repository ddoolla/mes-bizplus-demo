package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReceiptItemReader {

    private final ReceiptItemRepository receiptItemRepository;

    public List<ReceiptItem> getAll(Long receiptId) {
        return receiptItemRepository.findReceiptItems(receiptId);
    };

    public ReceiptItem getById(Long id) {
        return receiptItemRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.RECEIPT_ITEM_NOT_FOUND,
                        String.format("id: %d", id)
                ));
    }
}

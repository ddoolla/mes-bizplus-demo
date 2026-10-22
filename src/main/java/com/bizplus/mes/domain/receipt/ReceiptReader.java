package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiptReader {

    private final ReceiptRepository receiptRepository;

    public Receipt getById(Long id) {
        return receiptRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.RECEIPT_NOT_FOUND,
                        String.format("id: %d", id)
                ));
    }
}

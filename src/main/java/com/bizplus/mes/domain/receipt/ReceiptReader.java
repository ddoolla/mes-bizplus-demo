package com.bizplus.mes.domain.receipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiptReader {

    private final ReceiptRepository receiptRepository;
}

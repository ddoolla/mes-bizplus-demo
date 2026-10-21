package com.bizplus.mes.domain.material.receipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaterialReceiptReader {

    private final MaterialReceiptRepository materialReceiptRepository;
}


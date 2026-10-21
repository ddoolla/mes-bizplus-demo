package com.bizplus.mes.domain.receipt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptStatus {

    DRAFT("작성중"),
    COMPLETED("입고 완료");

    private final String description;
}

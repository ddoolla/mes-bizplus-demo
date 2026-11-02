package com.bizplus.mes.domain.receipt.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptItemStatus {

    PENDING("입고 대기"),
    COMPLETED("입고 완료"),
    ON_HOLD("입고 보류"),
    REJECTED("입고 거부");

    private final String description;
}

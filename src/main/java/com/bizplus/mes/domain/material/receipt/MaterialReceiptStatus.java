package com.bizplus.mes.domain.material.receipt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaterialReceiptStatus {

    PENDING("입고 대기"),
    COMPLETED("입고 완료"),
    CANCELED("입고 취소");

    private final String description;
}

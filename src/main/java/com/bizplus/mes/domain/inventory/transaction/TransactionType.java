package com.bizplus.mes.domain.inventory.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

    RECEIPT("입고"),
    PRODUCTION_IN("생산 입고"),
    PRODUCTION_OUT("생산 투입"),
    SHIPMENT("출고"),
    ADJUSTMENT("재고 조정"),
    RETURN("반품");

    private final String description;
}

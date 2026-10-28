package com.bizplus.mes.domain.sales.order.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SalesOrderItemStatus {

    NOT_SHIPPED("미출고"),
    PARTIALLY_SHIPPED("부분 출고"),
    SHIPPED("출고 완료");

    private final String description;
}

package com.bizplus.mes.domain.sales.order.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SalesOrderItemStatus {

    NOT_SHIPPED("미출하"),
    PARTIALLY_SHIPPED("부분 출하"),
    SHIPPED("출하 완료");

    private final String description;
}

package com.bizplus.mes.domain.sales.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SalesOrderStatus {

    DRAFT("작성중"),
    CONFIRMED("확정"),
    PARTIALLY_SHIPPED("부분 출고"),
    COMPLETED("출고 완료");

    private final String description;
}

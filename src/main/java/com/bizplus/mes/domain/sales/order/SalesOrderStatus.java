package com.bizplus.mes.domain.sales.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SalesOrderStatus {

    DRAFT("작성중"),
    CONFIRMED("확정"),
    COMPLETED("완료");

    private final String description;
}

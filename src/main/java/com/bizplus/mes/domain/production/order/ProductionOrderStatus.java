package com.bizplus.mes.domain.production.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductionOrderStatus {

    DRAFT("작성중"),
    CONFIRMED("확정"),
    IN_PROGRESS("생산중"),
    COMPLETED("완료");

    private final String description;
}

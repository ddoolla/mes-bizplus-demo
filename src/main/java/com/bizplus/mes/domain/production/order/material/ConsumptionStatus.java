package com.bizplus.mes.domain.production.order.material;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConsumptionStatus {

    PENDING("대기"),
    PARTIALLY_CONSUMED("부분 소진"),
    COMPLETED("소진 완료");

    private final String description;
}

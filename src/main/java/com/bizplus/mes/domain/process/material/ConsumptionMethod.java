package com.bizplus.mes.domain.process.material;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConsumptionMethod {

    MANUAL("수동 소진"),
    BACKFLUSH("자동 소진");

    private final String description;
}

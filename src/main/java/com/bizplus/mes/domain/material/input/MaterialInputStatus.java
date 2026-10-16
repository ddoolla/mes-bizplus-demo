package com.bizplus.mes.domain.material.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaterialInputStatus {

    PENDING("투입 대기"),
    COMPLETED("투입 완료"),
    CANCELED("투입 취소");

    private final String description;
}

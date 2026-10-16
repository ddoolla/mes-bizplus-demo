package com.bizplus.mes.domain.material.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaterialInputType {

    AUTO("자동 투입"),
    MANUAL("수동 투입");

    private final String description;
}

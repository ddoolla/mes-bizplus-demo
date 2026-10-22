package com.bizplus.mes.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageCode {

    CREATED("common.created"),
    UPDATED("common.updated"),
    DELETED("common.deleted"),
    CONFIRMED("common.confirmed"),
    COMPLETED("common.completed");

    private final String key;
}

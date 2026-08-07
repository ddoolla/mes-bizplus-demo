package com.bizplus.mes.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageCode {

    CREATED("common.created"),
    UPDATED("common.updated"),
    DELETED("common.deleted");

    private final String key;
}

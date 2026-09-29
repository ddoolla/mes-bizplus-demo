package com.bizplus.mes.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodePrefix {

    SALES_ORDER("SO"),
    PRODUCTION_ORDER("PO");

    private final String value;
}

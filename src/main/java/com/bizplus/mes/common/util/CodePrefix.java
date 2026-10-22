package com.bizplus.mes.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodePrefix {

    SALES_ORDER("SO"),
    PRODUCTION_ORDER("PO"),
    WORK_ORDER("WO"),
    RECEIPT("MR"),
    INCOMING_INSPECTION("IQC");

    private final String value;
}

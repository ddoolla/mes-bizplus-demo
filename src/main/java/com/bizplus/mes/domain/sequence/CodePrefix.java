package com.bizplus.mes.domain.sequence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodePrefix {

    SALES_ORDER("SO"),
    PRODUCTION_ORDER("PO"),
    WORK_ORDER("WO"),
    RECEIPT("MR"),
    INCOMING_INSPECTION("IQC"),
    IN_PROCESS_INSPECTION("IPQC"),
    SHIPMENT("S");

    private final String value;
}

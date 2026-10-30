package com.bizplus.mes.domain.inventory.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InventoryReferenceType {
    RECEIPT("자재 입고", "/receipts"),
    SHIPMENT("제품 출고", "/shipments"),
    PRODUCTION_ORDER("생산지시", "/production-orders"),
    WORK_ORDER("작업지시", "/work-orders");

    private final String description;
    private final String path;

    public String getDetailUrl(Long referenceId) {
        return path + "/" + referenceId;
    }
}

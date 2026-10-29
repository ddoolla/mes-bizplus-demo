package com.bizplus.mes.domain.shipment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShipmentStatus {

    DRAFT("작성중"),
    COMPLETED("출고 완료");

    private final String description;
}

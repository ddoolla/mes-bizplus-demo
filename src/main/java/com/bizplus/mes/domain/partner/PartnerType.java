package com.bizplus.mes.domain.partner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartnerType {

    SUPPLIER("매입처"),
    CUSTOMER("매출처"),
    BOTH("매입/매출처");
//    SUBCONTRACT("외주처");

    private final String description;
}

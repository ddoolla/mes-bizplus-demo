package com.bizplus.mes.domain.inspection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InspectionStatus {

    PENDING("검사대기"),
    COMPLETED("검사완료");

    private final String description;
}

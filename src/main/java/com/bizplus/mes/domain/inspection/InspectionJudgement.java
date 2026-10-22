package com.bizplus.mes.domain.inspection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InspectionJudgement {

    PASS("합격"),
    FAIL("불합격");

    private final String description;
}

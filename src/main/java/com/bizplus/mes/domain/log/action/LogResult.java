package com.bizplus.mes.domain.log.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogResult {

    SUCCESS("성공"),
    FAIL("실패");

    private final String description;
}

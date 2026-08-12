package com.bizplus.mes.domain.lot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LotStatus {

    ACTIVE("정상 사용"),
    HOLD("보류"),
    EXPIRED("유효기간 만료"),
    CLOSED("사용 종료");

    private final String description;
}

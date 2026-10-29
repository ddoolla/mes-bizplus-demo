package com.bizplus.mes.domain.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShelfLifeUnit {

    DAY("일"),
    MONTH("개월"),
    YEAR("년");

    private final String description;
}

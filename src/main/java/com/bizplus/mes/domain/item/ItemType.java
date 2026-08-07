package com.bizplus.mes.domain.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemType {

    FINISHED_GOODS("완제품"),
    SEMI_FINISHED_GOODS("반제품"),
    RAW_MATERIAL("원자재"),
    SUB_MATERIAL("부자재");

    private final String description;
}

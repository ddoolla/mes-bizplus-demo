package com.bizplus.mes.domain.uom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UomType {

    COUNT("개수"), // EA, PCS, BOX
    MASS("중량"), // KG, G, TON
    LENGTH("길이"), // MM, M
    AREA("면적"), // M2
    VOLUME("부피"); // L, ML

    private final String description;
}

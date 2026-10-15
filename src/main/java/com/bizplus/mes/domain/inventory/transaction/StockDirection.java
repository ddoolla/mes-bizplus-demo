package com.bizplus.mes.domain.inventory.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public enum StockDirection {

    IN(1),
    OUT(-1);

    private final int sign;

    public BigDecimal apply(BigDecimal quantity) {
        return quantity.multiply(BigDecimal.valueOf(sign));
    }
}

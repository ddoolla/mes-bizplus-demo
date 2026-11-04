package com.bizplus.mes.common.validator;

import java.math.BigDecimal;

public class QuantityValidator {

    public static void validateScale(BigDecimal quantity, Integer scale) {
        if (quantity == null || scale == null || scale < 0) {
            throw new IllegalArgumentException("수량 또는 소수 자릿수가 올바르지 않습니다.");
        }

        if (quantity.stripTrailingZeros().scale() > scale) {
            throw new IllegalArgumentException("수량의 소수 자릿수가 허용 범위를 초과했습니다.");
        }
    }
}

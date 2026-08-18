package com.bizplus.mes.domain.uom;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class UomValidator {

    public void validateQuantity(BigDecimal quantity, Uom uom) {

        if (quantity == null) {
            throw new IllegalArgumentException("수량은 필수입니다.");
        }

        try {
            quantity.setScale(
                    uom.getScale(),
                    RoundingMode.UNNECESSARY
            );
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UOM_QUANTITY_SCALE_EXCEEDED,
                    "quantity: " + quantity + ", scale: " + uom.getScale());
        }
    }
}

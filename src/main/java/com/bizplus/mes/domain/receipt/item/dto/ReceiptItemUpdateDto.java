package com.bizplus.mes.domain.receipt.item.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ReceiptItemUpdateDto {

    @NotNull
    private final Long id;
    private final Long uomId;

    @Positive
    private final BigDecimal quantity;
    private final BigDecimal unitPrice;
    private final Boolean inspectionAutoCreate;
    private final String remark;

    public ReceiptItemUpdateDto(Long id,
                                Long uomId,
                                BigDecimal quantity,
                                BigDecimal unitPrice,
                                Boolean inspectionAutoCreate,
                                String remark) {
        this.id = id;
        this.uomId = uomId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.inspectionAutoCreate = Boolean.TRUE.equals(inspectionAutoCreate);
        this.remark = remark;
    }
}

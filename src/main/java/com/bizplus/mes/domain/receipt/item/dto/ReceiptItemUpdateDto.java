package com.bizplus.mes.domain.receipt.item.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ReceiptItemUpdateDto {

    @NotNull
    private Long id;
    private Long uomId;

    @Positive
    private BigDecimal quantity;
    private String remark;
}

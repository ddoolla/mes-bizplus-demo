package com.bizplus.mes.domain.sales.order.item.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class SalesOrderItemUpdateDto {

    @NotNull
    private Long id;

    @NotNull
    @PositiveOrZero
    private final BigDecimal quantity;

    @PositiveOrZero
    private final BigDecimal unitPrice;

    private final String remark;
}

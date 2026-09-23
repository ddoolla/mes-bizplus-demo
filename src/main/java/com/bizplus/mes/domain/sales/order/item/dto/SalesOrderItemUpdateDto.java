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

    @PositiveOrZero
    private final BigDecimal unitPrice;

    @PositiveOrZero
    private final BigDecimal quantity;
    private final String remark;
}

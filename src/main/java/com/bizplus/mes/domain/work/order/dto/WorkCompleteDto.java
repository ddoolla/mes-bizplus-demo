package com.bizplus.mes.domain.work.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class WorkCompleteDto {

    @NotNull
    private Long productionOrderId;

    @NotNull
    @PositiveOrZero
    private BigDecimal productionQuantity;

    @NotNull
    @PositiveOrZero
    private BigDecimal goodQuantity;

    @NotNull
    @PositiveOrZero
    private BigDecimal defectQuantity;
    private String remark;
}

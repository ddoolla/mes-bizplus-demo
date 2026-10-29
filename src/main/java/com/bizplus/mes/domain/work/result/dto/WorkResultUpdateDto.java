package com.bizplus.mes.domain.work.result.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WorkResultUpdateDto {

    @NotNull
    @PositiveOrZero
    private BigDecimal productionQuantity;

    @NotNull
    @PositiveOrZero
    private BigDecimal goodQuantity;

    @NotNull
    @PositiveOrZero
    private BigDecimal defectQuantity;
    private LocalDateTime completedAt;
    private String remark;
}

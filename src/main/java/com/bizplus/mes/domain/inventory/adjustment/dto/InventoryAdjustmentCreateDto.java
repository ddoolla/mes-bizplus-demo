package com.bizplus.mes.domain.inventory.adjustment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class InventoryAdjustmentCreateDto {

    @NotNull
    private Long itemId;
    private Long lotId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    @PositiveOrZero
    private BigDecimal beforeQuantity;

    @NotNull
    @PositiveOrZero
    private BigDecimal afterQuantity;
    private String reason;
}

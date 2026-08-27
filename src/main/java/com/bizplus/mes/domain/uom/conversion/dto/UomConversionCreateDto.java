package com.bizplus.mes.domain.uom.conversion.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class UomConversionCreateDto {

    @NotNull
    private Long fromUomId;

    @NotNull
    private Long toUomId;

    @Positive
    private BigDecimal factor;
}

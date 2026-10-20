package com.bizplus.mes.domain.material.input.dto;

import com.bizplus.mes.domain.material.input.MaterialInputType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MaterialInputCreateDto {

    @NotNull
    private Long workOrderId;

    @NotNull
    private Long itemId;
    private Long lotId;

    @NotNull
    private Long uomId;

    @NotNull
    private MaterialInputType type;

    @NotNull
    private BigDecimal quantity;
}

package com.bizplus.mes.domain.bom.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BomItemUpdateDto {

    @NotNull
    private Long uomId;
    private BigDecimal quantity;
}

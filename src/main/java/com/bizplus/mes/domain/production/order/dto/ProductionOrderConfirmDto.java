package com.bizplus.mes.domain.production.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductionOrderConfirmDto {

    @NotNull
    private Long itemId;
}

package com.bizplus.mes.domain.work.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class WorkStopDto {

    private BigDecimal productionQuantity;
    private BigDecimal goodQuantity;
    private BigDecimal defectQuantity;
}

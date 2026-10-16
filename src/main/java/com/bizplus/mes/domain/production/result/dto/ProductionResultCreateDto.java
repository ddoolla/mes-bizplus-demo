package com.bizplus.mes.domain.production.result.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductionResultCreateDto {

    private Long productionOrderId;
    private Long workOrderId;
    private BigDecimal totalQuantity;
    private BigDecimal goodQuantity;
    private BigDecimal defectQuantity;
    private LocalDateTime completedAt;
}

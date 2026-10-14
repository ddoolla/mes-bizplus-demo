package com.bizplus.mes.domain.work.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class WorkOrderDetailDto {

    private WorkOrderDto workOrder;
    private BigDecimal progressRate;
}

package com.bizplus.mes.domain.work.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkOrderCreateDto {

    private List<Long> productionOrderProcessId;
}

package com.bizplus.mes.domain.work.order.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkOrderCreateDto {

    @NotEmpty
    private List<Long> productionOrderProcessIds;
}

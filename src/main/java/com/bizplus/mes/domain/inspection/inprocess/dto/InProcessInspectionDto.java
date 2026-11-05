package com.bizplus.mes.domain.inspection.inprocess.dto;

import com.bizplus.mes.domain.inspection.dto.InspectionDto;
import com.bizplus.mes.domain.work.order.dto.WorkOrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InProcessInspectionDto {

    private InspectionDto inspection;
    private WorkOrderDto workOrder;
}

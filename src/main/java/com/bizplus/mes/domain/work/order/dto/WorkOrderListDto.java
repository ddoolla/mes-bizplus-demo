package com.bizplus.mes.domain.work.order.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkOrderListDto {

    private List<WorkOrderDto> workOrders;
    private Pagination pagination;
}

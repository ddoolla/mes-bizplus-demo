package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.domain.work.order.dto.WorkOrderCreateDto;

public interface WorkOrderService {

    void createWorkOrders(WorkOrderCreateDto dto);
}

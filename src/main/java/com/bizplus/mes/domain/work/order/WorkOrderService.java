package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.domain.work.order.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkOrderService {

    WorkOrderListDto getWorkOrders(WorkOrderSearchDto dto, Pageable pageable);

    List<WorkOrderDto> getWorkOrders(Long ProductionOrderId);

    WorkOrderDto getWorkOrder(Long id);

    /**
     * 생산지시 등록 시 자동생성용
     */
    void autoCreateWorkOrders(Long productionOrderId, WorkOrderCreateDto dto);

    /**
     * 수동 추가용
     */
    void createWorkOrders(WorkOrderCreateDto dto);

    void updateWorkOrders(List<WorkOrderUpdateDto> dtoList);

    void deleteWorkOrders(List<Long> ids);
}

package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.domain.work.order.dto.WorkOrderDto;
import com.bizplus.mes.domain.work.order.dto.WorkOrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkOrderQueryRepository {

    Page<WorkOrderDto> fetchWorkOrders(WorkOrderSearchDto dto, Pageable pageable);

    Page<WorkOrderDto> fetchWorkOrdersByUserId(Long userId, WorkOrderSearchDto dto, Pageable pageable);

    List<WorkOrderDto> fetchWorkOrders(Long productionOrderId);

    List<WorkOrderDto> fetchAllByLotId(Long lotId);

    Optional<WorkOrderDto> fetchWorkOrder(Long id);

    Optional<WorkOrder> findWorkOrderForUpdate(Long id);

    String findMaxOrderNo(LocalDate date);

    boolean existsIncompleteWorkOrder(Long productionOrderId);
}

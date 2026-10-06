package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.domain.work.order.dto.WorkOrderDto;
import com.bizplus.mes.domain.work.order.dto.WorkOrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkOrderQueryRepository {

    Page<WorkOrderDto> findWorkOrders(WorkOrderSearchDto dto, Pageable pageable);

    List<WorkOrderDto> findWorkOrders(Long productionOrderId);

    Optional<WorkOrderDto> findWorkOrder(Long id);

    String findMaxOrderNo(LocalDate date);
}

package com.bizplus.mes.domain.production.order.facade;

import com.bizplus.mes.domain.production.order.ProductionOrderService;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderUpdateDto;
import com.bizplus.mes.domain.work.order.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductionOrderUpdateService {

    private final ProductionOrderService productionOrderService;
    private final WorkOrderService workOrderService;

    @Transactional
    public void update(Long id, ProductionOrderUpdateDto dto) {
        // 1. 생산지시 정보 업데이트
        productionOrderService.updateProductionOrder(id, dto);

        // 2. 작업지시 정보 업데이트
        workOrderService.updateWorkOrders(dto.getWorkOrders());
    }
}

package com.bizplus.mes.domain.production.order.facade;

import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.production.order.ProductionOrderReader;
import com.bizplus.mes.domain.production.order.ProductionOrderStatus;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderUpdateDto;
import com.bizplus.mes.domain.production.order.material.ProductionOrderMaterialService;
import com.bizplus.mes.domain.work.order.WorkOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductionOrderUpdateService {

    private final ProductionOrderReader productionOrderReader;

    private final ProductionOrderMaterialService productionOrderMaterialService;
    private final WorkOrderService workOrderService;

    @Transactional
    public void update(Long id, ProductionOrderUpdateDto dto) {
        ProductionOrder productionOrder = productionOrderReader.getById(id);

        // 상태에 따라 업데이트 제한
        if (productionOrder.getStatus() == ProductionOrderStatus.DRAFT) {
            // 1. 생산지시 정보 업데이트
            productionOrder.update(
                    dto.getQuantity(),
                    dto.getDueDate(),
                    dto.getRemark()
            );

            // 2. 생산지시 자재 정보 업데이트
            productionOrderMaterialService.update(id, dto.getQuantity());

            // 3. 작업지시 정보 업데이트
            workOrderService.updateWorkOrders(dto.getWorkOrders());

        } else if (productionOrder.getStatus() == ProductionOrderStatus.COMPLETED) {
            productionOrder.updateRemark(dto.getRemark());

        } else {
            productionOrder.updateRemark(dto.getRemark());
            workOrderService.updateWorkOrders(dto.getWorkOrders());
        }
    }
}

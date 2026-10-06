package com.bizplus.mes.domain.production.order.facade;

import com.bizplus.mes.domain.production.order.ProductionOrderService;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderCreateDto;
import com.bizplus.mes.domain.production.order.material.ProductionOrderMaterialService;
import com.bizplus.mes.domain.production.order.process.ProductionOrderProcessService;
import com.bizplus.mes.domain.work.order.WorkOrderService;
import com.bizplus.mes.domain.work.order.dto.WorkOrderCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionOrderCreateService {

    private final ProductionOrderService productionOrderService;
    private final ProductionOrderProcessService productionOrderProcessService;
    private final ProductionOrderMaterialService productionOrderMaterialService;
    private final WorkOrderService workOrderService;

    @Transactional
    public Long create(ProductionOrderCreateDto dto) {
        // 1. 생산 지시 생성
        Long newProductionOrderId = productionOrderService.createProductionOrder(dto);

        // 2. BOM 정보, 공정 라우팅 정보 스냅샷
        List<Long> newPopIds = productionOrderProcessService.createProductionOrderProcesses(newProductionOrderId);
        productionOrderMaterialService.createProductionOrderMaterials(newProductionOrderId);

        // 3. 작업지시 자동 생성
        workOrderService.autoCreateWorkOrders(
                newProductionOrderId,
                new WorkOrderCreateDto(newPopIds)
        );

        return newProductionOrderId;
    }
}

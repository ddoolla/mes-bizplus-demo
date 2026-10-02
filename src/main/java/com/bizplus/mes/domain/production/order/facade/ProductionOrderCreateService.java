package com.bizplus.mes.domain.production.order.facade;

import com.bizplus.mes.domain.production.order.ProductionOrderService;
import com.bizplus.mes.domain.production.order.material.ProductionOrderMaterialService;
import com.bizplus.mes.domain.production.order.process.ProductionOrderProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductionOrderCreateService {

    private final ProductionOrderService productionOrderService;
    private final ProductionOrderProcessService productionOrderProcessService;
    private final ProductionOrderMaterialService productionOrderMaterialService;
}

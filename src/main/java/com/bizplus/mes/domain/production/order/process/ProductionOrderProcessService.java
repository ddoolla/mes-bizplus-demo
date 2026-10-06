package com.bizplus.mes.domain.production.order.process;

import com.bizplus.mes.domain.production.order.process.dto.ProductionOrderProcessDto;

import java.util.List;

public interface ProductionOrderProcessService {

    List<ProductionOrderProcessDto> getProductionOrderProcesses(Long productionOrderId);

    List<Long> createProductionOrderProcesses(Long productionOrderId);
}

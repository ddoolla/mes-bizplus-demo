package com.bizplus.mes.domain.production.order.process;

import com.bizplus.mes.domain.production.order.process.dto.ProductionOrderProcessDto;

import java.util.List;

public interface ProductionOrderProcessQueryRepository {

    List<ProductionOrderProcessDto> fetchAll(Long productionOrderId);

    Integer findLastStepNo(Long productionOrderId);
}

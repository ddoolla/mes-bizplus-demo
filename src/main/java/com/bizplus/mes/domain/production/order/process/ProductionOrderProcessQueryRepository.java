package com.bizplus.mes.domain.production.order.process;

public interface ProductionOrderProcessQueryRepository {

    Integer findLastStepNo(Long productionOrderId);
}

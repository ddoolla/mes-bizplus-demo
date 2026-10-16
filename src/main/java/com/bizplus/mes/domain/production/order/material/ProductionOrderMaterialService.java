package com.bizplus.mes.domain.production.order.material;

import java.math.BigDecimal;

public interface ProductionOrderMaterialService {

    void create(Long productionOrderId);

    void update(Long productionOrderId, BigDecimal plannedQuantity);
}

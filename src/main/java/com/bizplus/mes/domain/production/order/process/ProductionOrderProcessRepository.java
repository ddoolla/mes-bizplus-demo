package com.bizplus.mes.domain.production.order.process;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionOrderProcessRepository extends JpaRepository<ProductionOrderProcess, Long> {

    List<ProductionOrderProcess> findByProductionOrderIdAndDeletedAtIsNull(Long productionOrderId);
}

package com.bizplus.mes.domain.production.order.process;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductionOrderProcessRepository extends JpaRepository<ProductionOrderProcess, Long> {

    Optional<ProductionOrderProcess> findByIdAndDeletedAtIsNull(Long id);

    List<ProductionOrderProcess> findByProductionOrderIdAndDeletedAtIsNull(Long productionOrderId);
}

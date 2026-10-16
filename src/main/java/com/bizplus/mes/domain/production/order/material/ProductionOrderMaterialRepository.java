package com.bizplus.mes.domain.production.order.material;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionOrderMaterialRepository extends JpaRepository<ProductionOrderMaterial, Long> {

    List<ProductionOrderMaterial> findAllByProductionOrderId(Long productionOrderId);
}

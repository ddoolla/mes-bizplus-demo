package com.bizplus.mes.domain.production.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductionOrderRepository extends
        JpaRepository<ProductionOrder, Long>, ProductionOrderQueryRepository {

    Optional<ProductionOrder> findByIdAndDeletedAtIsNull(Long id);
}

package com.bizplus.mes.domain.inventory.adjustment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAdjustmentRepository
        extends JpaRepository<InventoryAdjustment, Long>, InventoryAdjustmentQueryRepository {
}

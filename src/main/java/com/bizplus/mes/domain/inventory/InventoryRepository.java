package com.bizplus.mes.domain.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long>, InventoryQueryRepository {
}

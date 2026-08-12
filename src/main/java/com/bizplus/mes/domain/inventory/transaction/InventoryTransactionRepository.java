package com.bizplus.mes.domain.inventory.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransactionRepository extends
        JpaRepository<InventoryTransaction, Long>, InventoryTransactionQueryRepository {
}

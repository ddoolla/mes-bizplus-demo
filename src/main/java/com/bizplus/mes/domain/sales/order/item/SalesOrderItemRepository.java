package com.bizplus.mes.domain.sales.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesOrderItemRepository extends
        JpaRepository<SalesOrderItem, Long>, SalesOrderItemQueryRepository {

    Optional<SalesOrderItem> findByIdAndDeletedAtIsNull(Long id);
}

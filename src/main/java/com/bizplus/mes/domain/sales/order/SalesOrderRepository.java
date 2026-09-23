package com.bizplus.mes.domain.sales.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long>, SalesOrderQueryRepository {

    Optional<SalesOrder> findByIdAndDeletedAtIsNull(Long id);
}

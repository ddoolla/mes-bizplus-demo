package com.bizplus.mes.domain.work.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long>, WorkOrderQueryRepository {

    Optional<WorkOrder> findByIdAndDeletedAtIsNull(Long id);
}

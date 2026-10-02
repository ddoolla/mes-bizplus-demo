package com.bizplus.mes.domain.work.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long>, WorkOrderQueryRepository {
}

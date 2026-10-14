package com.bizplus.mes.domain.work.result;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkResultRepository extends JpaRepository<WorkResult, Long>, WorkResultQueryRepository {

    boolean existsByWorkOrderId(Long workOrderId);
}

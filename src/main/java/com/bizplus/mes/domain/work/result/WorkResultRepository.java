package com.bizplus.mes.domain.work.result;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkResultRepository extends JpaRepository<WorkResult, Long>, WorkResultQueryRepository {

    Optional<WorkResult> findByWorkOrderId(Long workOrderId);

    boolean existsByWorkOrderId(Long workOrderId);
}

package com.bizplus.mes.domain.worker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long>, WorkerQueryRepository {

    Optional<Worker> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}

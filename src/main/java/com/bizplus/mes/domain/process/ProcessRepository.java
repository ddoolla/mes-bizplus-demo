package com.bizplus.mes.domain.process;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process, Long>, ProcessQueryRepository {

    Optional<Process> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}

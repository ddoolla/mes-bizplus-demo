package com.bizplus.mes.domain.inspection.inprocess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InProcessInspectionRepository
        extends JpaRepository<InProcessInspection, Long>, InProcessInspectionQueryRepository {

    Optional<InProcessInspection> findByIdAndDeletedAtIsNull(Long id);
}

package com.bizplus.mes.domain.inspection.incoming;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomingInspectionRepository
        extends JpaRepository<IncomingInspection, Long>, IncomingInspectionQueryRepository {

    Optional<IncomingInspection> findByIdAndDeletedAtIsNull(Long id);
}

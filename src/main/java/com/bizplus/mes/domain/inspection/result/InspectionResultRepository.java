package com.bizplus.mes.domain.inspection.result;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InspectionResultRepository
        extends JpaRepository<InspectionResult, Long>, InspectionResultQueryRepository {

    Optional<InspectionResult> findByIdAndDeletedAtIsNull(Long id);
}

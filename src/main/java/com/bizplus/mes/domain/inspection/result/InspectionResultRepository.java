package com.bizplus.mes.domain.inspection.result;

import com.bizplus.mes.domain.inspection.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InspectionResultRepository
        extends JpaRepository<InspectionResult, Long>, InspectionResultQueryRepository {

    Optional<InspectionResult> findByIdAndDeletedAtIsNull(Long id);

    List<InspectionResult> inspection(Inspection inspection);
}

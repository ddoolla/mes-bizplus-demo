package com.bizplus.mes.domain.inspection.spec;

import com.bizplus.mes.domain.inspection.spec.dto.InspectionSpecDto;
import com.bizplus.mes.domain.inspection.spec.dto.InspectionSpecSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InspectionSpecQueryRepository {

    Page<InspectionSpecDto> findInspectionSpecs(InspectionSpecSearchDto dto, Pageable pageable);

    Optional<InspectionSpecDto> findInspectionSpec(Long id);

    void resetPrimary(Long itemId, Long processId, InspectionType type);
}

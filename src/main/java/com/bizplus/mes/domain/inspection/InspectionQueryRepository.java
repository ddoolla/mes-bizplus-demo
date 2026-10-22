package com.bizplus.mes.domain.inspection;

import com.bizplus.mes.domain.inspection.dto.InspectionDto;
import com.bizplus.mes.domain.inspection.dto.InspectionSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InspectionQueryRepository {

    Page<InspectionDto> fetchInspections(InspectionSearchDto dto, Pageable pageable);
}

package com.bizplus.mes.domain.inspection.incoming;

import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionSearchDto;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IncomingInspectionQueryRepository {

    Page<IncomingInspectionSummaryDto> fetchAll(IncomingInspectionSearchDto dto, Pageable pageable);
}

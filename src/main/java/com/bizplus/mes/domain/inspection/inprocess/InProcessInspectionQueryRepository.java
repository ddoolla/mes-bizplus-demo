package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSearchDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InProcessInspectionQueryRepository {

    Page<InProcessInspectionSummaryDto> fetchAllSummaries(InProcessInspectionSearchDto dto, Pageable pageable);

    List<InProcessInspectionSummaryDto> fetchAllSummariesByLotId(Long lotId);
}

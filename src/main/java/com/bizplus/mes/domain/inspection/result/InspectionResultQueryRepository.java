package com.bizplus.mes.domain.inspection.result;

import com.bizplus.mes.domain.inspection.result.dto.InspectionResultDto;

import java.util.List;

public interface InspectionResultQueryRepository {

    List<InspectionResultDto> fetchInspectionResults(Long inspectionId);
}

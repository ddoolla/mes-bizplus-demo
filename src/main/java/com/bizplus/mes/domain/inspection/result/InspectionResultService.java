package com.bizplus.mes.domain.inspection.result;

import com.bizplus.mes.domain.inspection.result.dto.InspectionResultDto;

import java.util.List;

public interface InspectionResultService {

    List<InspectionResultDto> getAllByInspectionId(Long inspectionId);
}

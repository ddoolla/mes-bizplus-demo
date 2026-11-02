package com.bizplus.mes.domain.inspection.result;

import com.bizplus.mes.domain.inspection.result.dto.InspectionResultDto;
import com.bizplus.mes.domain.inspection.result.dto.InspectionResultUpdateDto;

import java.util.List;

public interface InspectionResultService {

    List<InspectionResultDto> getAll(Long inspectionId);

    void createAll(Long inspectionId, Long inspectionSpecId);

    void updateAll(List<InspectionResultUpdateDto> dtoList);
}

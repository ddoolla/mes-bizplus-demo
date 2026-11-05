package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionListDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSearchDto;
import org.springframework.data.domain.Pageable;

public interface InProcessInspectionService {

    InProcessInspectionListDto getAll(InProcessInspectionSearchDto dto, Pageable pageable);
}

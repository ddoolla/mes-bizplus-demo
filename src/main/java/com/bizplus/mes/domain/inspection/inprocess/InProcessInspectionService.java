package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionListDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSearchDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface InProcessInspectionService {

    InProcessInspectionListDto getAll(InProcessInspectionSearchDto dto, Pageable pageable);

    Long autoCreate(Long workOrderId, Long inspectionSpecId, LocalDate date);
}

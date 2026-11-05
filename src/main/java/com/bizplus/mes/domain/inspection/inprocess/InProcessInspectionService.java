package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionListDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSearchDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface InProcessInspectionService {

    InProcessInspectionListDto getAll(InProcessInspectionSearchDto dto, Pageable pageable);

    InProcessInspectionDto getById(Long id);

    Long autoCreate(Long workOrderId, Long inspectionSpecId, LocalDate date);
}

package com.bizplus.mes.domain.inspection.inprocess.dto;

import com.bizplus.mes.domain.inspection.dto.InspectionDto;
import com.bizplus.mes.domain.work.result.dto.WorkResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InProcessInspectionDto {

    private Long id;
    private InspectionDto inspection;
    private WorkResultDto workResult;
}

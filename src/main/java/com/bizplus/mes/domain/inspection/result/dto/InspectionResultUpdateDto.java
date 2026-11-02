package com.bizplus.mes.domain.inspection.result.dto;

import com.bizplus.mes.domain.inspection.InspectionJudgement;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionResultUpdateDto {

    private Long id;
    private String measuredValue;
    private InspectionJudgement judgement;
    private String remark;
}

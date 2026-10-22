package com.bizplus.mes.domain.inspection.dto;

import com.bizplus.mes.domain.inspection.InspectionJudgement;
import com.bizplus.mes.domain.inspection.InspectionStatus;
import com.bizplus.mes.domain.inspection.InspectionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class InspectionSearchDto {

    private String inspectionNo;
    private InspectionType type;
    private InspectionStatus status;
    private InspectionJudgement judgement;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}

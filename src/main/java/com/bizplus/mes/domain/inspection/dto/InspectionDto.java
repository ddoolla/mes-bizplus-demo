package com.bizplus.mes.domain.inspection.dto;

import com.bizplus.mes.domain.inspection.InspectionJudgement;
import com.bizplus.mes.domain.inspection.InspectionStatus;
import com.bizplus.mes.domain.inspection.InspectionType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class InspectionDto {

    private final Long id;
    private final String inspectionNo;
    private final LocalDate date;
    private final InspectionType type;
    private final InspectionStatus status;
    private final InspectionJudgement judgement;
    private final String remark;
    private final InspectionSpecInfo inspectionSpec;
    private final InspectorInfo inspector;

    @QueryProjection
    public InspectionDto(Long id,
                         String inspectionNo,
                         LocalDate date,
                         InspectionType type,
                         InspectionStatus status,
                         InspectionJudgement judgement,
                         String remark,
                         Long inspectionSpecId,
                         String inspectionSpecCode,
                         String inspectionSpecName,
                         Long inspectorId,
                         String inspectorName) {
        this.id = id;
        this.inspectionNo = inspectionNo;
        this.date = date;
        this.type = type;
        this.status = status;
        this.judgement = judgement;
        this.remark = remark;
        this.inspectionSpec = new InspectionSpecInfo(
                inspectionSpecId,
                inspectionSpecCode,
                inspectionSpecName
        );
        this.inspector = new InspectorInfo(
                inspectorId,
                inspectorName
        );
    }

    public record InspectionSpecInfo(Long id, String code, String name) {
    }

    public record InspectorInfo(Long id, String name) {
    }
}

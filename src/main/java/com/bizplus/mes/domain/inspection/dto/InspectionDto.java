package com.bizplus.mes.domain.inspection.dto;

import com.bizplus.mes.domain.inspection.InspectionJudgement;
import com.bizplus.mes.domain.inspection.InspectionStatus;
import com.bizplus.mes.domain.inspection.InspectionType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class InspectionDto {

    private final Long id;
    private final String inspectionNo;
    private final LocalDate date;
    private final BigDecimal inspectedQuantity;
    private final BigDecimal passedQuantity;
    private final BigDecimal failedQuantity;
    private final InspectionType type;
    private final InspectionStatus status;
    private final InspectionJudgement judgement;
    private final String remark;
    private final InspectionSpecInfo inspectionSpec;
    private final InspectorInfo inspector;
    private final UomInfo uom;

    @QueryProjection
    public InspectionDto(Long id,
                         String inspectionNo,
                         LocalDate date,
                         BigDecimal inspectedQuantity,
                         BigDecimal passedQuantity,
                         BigDecimal failedQuantity,
                         InspectionType type,
                         InspectionStatus status,
                         InspectionJudgement judgement,
                         String remark,
                         Long inspectionSpecId,
                         String inspectionSpecCode,
                         String inspectionSpecName,
                         Long inspectorId,
                         String inspectorName,
                         Long uomId,
                         String uomCode,
                         Integer uomScale) {
        this.id = id;
        this.inspectionNo = inspectionNo;
        this.date = date;
        this.inspectedQuantity = inspectedQuantity;
        this.passedQuantity = passedQuantity;
        this.failedQuantity = failedQuantity;
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
        this.uom = new UomInfo(
                uomId,
                uomCode,
                uomScale
        );
    }

    public record InspectionSpecInfo(Long id, String code, String name) {
    }

    public record InspectorInfo(Long id, String name) {
    }

    public record UomInfo(Long id, String code, Integer scale) {
    }
}

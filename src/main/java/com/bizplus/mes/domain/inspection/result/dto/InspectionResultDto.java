package com.bizplus.mes.domain.inspection.result.dto;

import com.bizplus.mes.domain.inspection.InspectionJudgement;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class InspectionResultDto {

    private final Long id;
    private final Long inspectionSpecItemId;
    private final Long itemId;
    private final String itemGroup;
    private final String itemCode;
    private final String itemName;
    private final String standard;
    private final String method;
    private final String specRemark;
    private final Integer sortOrder;
    private final String measuredValue;
    private final InspectionJudgement judgement;
    private final String remark;

    @QueryProjection
    public InspectionResultDto(Long id,
                               Long inspectionSpecItemId,
                               Long itemId,
                               String itemGroup,
                               String itemCode,
                               String itemName,
                               String standard,
                               String method,
                               String specRemark,
                               Integer sortOrder,
                               String measuredValue,
                               InspectionJudgement judgement,
                               String remark) {
        this.id = id;
        this.inspectionSpecItemId = inspectionSpecItemId;
        this.itemId = itemId;
        this.itemGroup = itemGroup;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.standard = standard;
        this.method = method;
        this.specRemark = specRemark;
        this.sortOrder = sortOrder;
        this.measuredValue = measuredValue;
        this.judgement = judgement;
        this.remark = remark;
    }
}

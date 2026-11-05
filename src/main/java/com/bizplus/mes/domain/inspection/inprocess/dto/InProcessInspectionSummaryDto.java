package com.bizplus.mes.domain.inspection.inprocess.dto;

import com.bizplus.mes.domain.inspection.InspectionJudgement;
import com.bizplus.mes.domain.inspection.InspectionStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class InProcessInspectionSummaryDto {

    private final Long id;
    private final String no;
    private final LocalDate date;
    private final LocalDate workDate;
    private final String itemCode;
    private final String itemName;
    private final String lotNo;
    private final BigDecimal quantity;
    private final InspectionStatus status;
    private final InspectionJudgement judgement;

    @QueryProjection
    public InProcessInspectionSummaryDto(Long id,
                                         String no,
                                         LocalDate date,
                                         LocalDate workDate,
                                         String itemCode,
                                         String itemName,
                                         String lotNo,
                                         BigDecimal quantity,
                                         InspectionStatus status,
                                         InspectionJudgement judgement) {
        this.id = id;
        this.no = no;
        this.date = date;
        this.workDate = workDate;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.lotNo = lotNo;
        this.quantity = quantity;
        this.status = status;
        this.judgement = judgement;
    }
}

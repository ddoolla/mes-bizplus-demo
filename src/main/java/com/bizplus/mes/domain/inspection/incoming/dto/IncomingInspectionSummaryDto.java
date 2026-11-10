package com.bizplus.mes.domain.inspection.incoming.dto;

import com.bizplus.mes.domain.inspection.InspectionJudgement;
import com.bizplus.mes.domain.inspection.InspectionStatus;
import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class IncomingInspectionSummaryDto {

    private final Long id;
    private final String no;
    private final LocalDate date;
    private final String receiptNo;
    private final LocalDate receiptDate;
    private final String itemCode;
    private final String itemName;
    private final ItemType itemType;
    private final String itemSpec;
    private final String LotNo;
    private final BigDecimal quantity;
    private final String uomCode;
    private final Integer uomScale;
    private final String inspector;
    private final InspectionStatus status;
    private final InspectionJudgement judgement;

    @QueryProjection
    public IncomingInspectionSummaryDto(Long id,
                                        String no,
                                        LocalDate date,
                                        String receiptNo,
                                        LocalDate receiptDate,
                                        String itemCode,
                                        String itemName,
                                        ItemType itemType,
                                        String itemSpec,
                                        String lotNo,
                                        BigDecimal quantity,
                                        String uomCode,
                                        Integer uomScale,
                                        String inspector,
                                        InspectionStatus status,
                                        InspectionJudgement judgement) {
        this.id = id;
        this.no = no;
        this.date = date;
        this.receiptNo = receiptNo;
        this.receiptDate = receiptDate;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemSpec = itemSpec;
        LotNo = lotNo;
        this.quantity = quantity;
        this.uomCode = uomCode;
        this.uomScale = uomScale;
        this.inspector = inspector;
        this.status = status;
        this.judgement = judgement;
    }
}

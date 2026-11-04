package com.bizplus.mes.domain.inspection.incoming.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class IncomingInspectionCreateDto {

    private Long receiptItemId;
    private Long inspectionSpecId;
    private Long uomId;
    private LocalDate date;
    private BigDecimal quantity;
    private String remark;
}

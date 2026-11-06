package com.bizplus.mes.domain.inspection.inprocess.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class InProcessInspectionCreateDto {

    private Long workResultId;
    private Long inspectionSpecId;
    private Long uomId;
    private LocalDate date;
    private BigDecimal quantity;
    private String remark;
}

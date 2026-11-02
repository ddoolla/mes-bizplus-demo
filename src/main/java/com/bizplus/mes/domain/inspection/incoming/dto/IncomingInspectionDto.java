package com.bizplus.mes.domain.inspection.incoming.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IncomingInspectionDto {

    private Long inspectionId;
    private Long receiptItemId;
}

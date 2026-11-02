package com.bizplus.mes.domain.inspection.incoming.dto;

import com.bizplus.mes.domain.inspection.dto.InspectionDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IncomingInspectionDto {

    private Long id;
    private InspectionDto inspection;
    private ReceiptItemDto receiptItem;
}

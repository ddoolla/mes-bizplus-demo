package com.bizplus.mes.domain.receipt.item.dto;

import com.bizplus.mes.domain.uom.conversion.dto.ConvertibleUomDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReceiptItemEditDto {

    private ReceiptItemDto receiptItem;
    private List<ConvertibleUomDto> convertibleUoms;
}

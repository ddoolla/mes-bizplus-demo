package com.bizplus.mes.domain.receipt.item.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReceiptItemListDto {

    private List<ReceiptItemDto> receiptItems;
    private Pagination pagination;
}

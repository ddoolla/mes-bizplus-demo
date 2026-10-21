package com.bizplus.mes.domain.receipt.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReceiptListDto {

    private List<ReceiptDto> receipts;
    private Pagination pagination;
}

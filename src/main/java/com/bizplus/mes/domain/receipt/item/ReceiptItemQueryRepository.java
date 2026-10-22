package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemDto;

import java.util.List;

public interface ReceiptItemQueryRepository {

    List<ReceiptItemDto> fetchReceiptItems(Long receiptId);

    List<ReceiptItem> findReceiptItems(Long receiptId);
}

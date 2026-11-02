package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemDto;

import java.util.List;
import java.util.Optional;

public interface ReceiptItemQueryRepository {

    List<ReceiptItemDto> fetchReceiptItems(Long receiptId);

    List<ReceiptItem> findReceiptItems(Long receiptId);

    Optional<ReceiptItemDto> fetchById(Long id);
}

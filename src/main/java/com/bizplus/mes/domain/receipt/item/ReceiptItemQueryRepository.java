package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReceiptItemQueryRepository {

    Page<ReceiptItemDto> fetchAllForInspection(ReceiptItemSearchDto dto, Pageable pageable);

    List<ReceiptItemDto> fetchReceiptItems(Long receiptId);

    List<ReceiptItem> findReceiptItems(Long receiptId);

    Optional<ReceiptItemDto> fetchById(Long id);
}

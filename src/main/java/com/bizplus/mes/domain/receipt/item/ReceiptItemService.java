package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemCreateDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemEditDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemUpdateDto;

import java.util.List;

public interface ReceiptItemService {

    // 자재 입고 상세 페이지용
    List<ReceiptItemDto> getAll(Long receiptId);

    // 자재 입고 수정 페이지용
    List<ReceiptItemEditDto> getAllForEdit(Long receiptId);

    void create(Long receiptId, ReceiptItemCreateDto dto);

    void update(List<ReceiptItemUpdateDto> dtoList);
}

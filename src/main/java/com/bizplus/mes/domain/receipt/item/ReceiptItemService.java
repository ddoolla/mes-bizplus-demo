package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.domain.receipt.item.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReceiptItemService {

    // 자재 입고 상세 페이지용
    List<ReceiptItemDto> getAll(Long receiptId);

    // 자재 입고 수정 페이지용
    List<ReceiptItemEditDto> getAllForEdit(Long receiptId);

    ReceiptItemListDto getAllForInspection(ReceiptItemSearchDto dto, Pageable pageable);

    ReceiptItemDto getById(Long id);

    void create(Long receiptId, ReceiptItemCreateDto dto);

    void update(List<ReceiptItemUpdateDto> dtoList);

    void deleteAll(List<Long> ids);
}

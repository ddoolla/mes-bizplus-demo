package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReceiptItemApiController {

    private final MessageService messageService;
    private final ReceiptItemService receiptItemService;

    @PostMapping("/api/receipts/{receiptId}/items")
    @PreAuthorize("hasAuthority('RECEIPT_CREATE')")
    public ResponseEntity<ApiResponse<Void>> create(@PathVariable Long receiptId,
                                                    @RequestBody @Valid ReceiptItemCreateDto dto) {
        receiptItemService.create(receiptId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @DeleteMapping("/api/receipt-items")
    @PreAuthorize("hasAuthority('RECEIPT_DELETE')")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody List<Long> ids) {
        receiptItemService.deleteAll(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}

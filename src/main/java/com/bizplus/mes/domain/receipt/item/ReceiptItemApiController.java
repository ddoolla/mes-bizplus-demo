package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReceiptItemApiController {

    private final MessageService messageService;
    private final ReceiptItemService receiptItemService;

    @PostMapping("/api/receipts/{receiptId}/items")
    public ResponseEntity<ApiResponse<Void>> create(@PathVariable Long receiptId,
                                                    @RequestBody @Valid ReceiptItemCreateDto dto) {
        receiptItemService.create(receiptId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }
}

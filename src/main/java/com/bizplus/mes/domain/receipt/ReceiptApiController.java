package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.receipt.dto.ReceiptSearchDto;
import com.bizplus.mes.domain.receipt.facade.ReceiptConfirmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/receipts")
@RequiredArgsConstructor
public class ReceiptApiController {

    private final MessageService messageService;
    private final ReceiptService receiptService;
    private final ReceiptExcelService receiptExcelService;
    private final ReceiptConfirmService receiptConfirmService;

    @GetMapping("/excel")
    @PreAuthorize("hasAuthority('RECEIPT_READ')")
    public ResponseEntity<byte[]> downloadExcel(ReceiptSearchDto dto) {
        byte[] excelData = receiptExcelService.download(dto);

        String fileName = "입고목록_" + LocalDate.now() + ".xlsx";

        ContentDisposition disposition = ContentDisposition.attachment()
                .filename(fileName, StandardCharsets.UTF_8)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .contentLength(excelData.length)
                .body(excelData);
    }

    @PostMapping("/{id}/confirm")
    @PreAuthorize("hasAuthority('RECEIPT_CREATE')")
    public ResponseEntity<ApiResponse<Void>> confirm(@PathVariable Long id) {
        receiptConfirmService.confirm(id);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CONFIRMED)));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('RECEIPT_DELETE')")
    @UserAction(menu = MenuCode.RECEIPT, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody List<Long> ids) {
        receiptService.deleteAll(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}

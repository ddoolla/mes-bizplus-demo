package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReceiptApiController {

    private final MessageService messageService;
    private final ReceiptService receiptService;

    @DeleteMapping("/api/receipts")
    @PreAuthorize("hasAuthority('RECEIPT_DELETE')")
    @UserAction(menu = MenuCode.RECEIPT, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody List<Long> ids) {
        receiptService.deleteAll(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}

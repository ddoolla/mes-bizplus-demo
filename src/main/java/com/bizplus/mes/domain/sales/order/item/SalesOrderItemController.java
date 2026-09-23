package com.bizplus.mes.domain.sales.order.item;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SalesOrderItemController {

    private final SalesOrderItemService salesOrderItemService;
    private final MessageService messageService;

    @PostMapping("/sales-orders/{salesOrderId}/items")
    @ResponseBody
    @PreAuthorize("hasAuthority('SALES_ORDER_CREATE')")
    public ResponseEntity<ApiResponse<Void>> createSalesOrderItems(@PathVariable Long salesOrderId,
                                                                   @RequestBody @Valid SalesOrderItemCreateDto dto) {
        salesOrderItemService.createSalesOrderItems(salesOrderId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }
}

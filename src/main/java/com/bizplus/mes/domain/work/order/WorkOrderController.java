package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.work.order.dto.WorkOrderCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/work-orders")
@RequiredArgsConstructor
public class WorkOrderController {

    private final MessageService messageService;
    private final WorkOrderService workOrderService;

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('WORK_ORDER_CREATE')")
    @UserAction(menu = MenuCode.WORK_ORDER, type = ActionType.CREATE)
    public ResponseEntity<ApiResponse<Void>> createWorkOrders(@RequestBody @Valid WorkOrderCreateDto dto) {
        workOrderService.createWorkOrders(dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('WORK_ORDER_DELETE')")
    @UserAction(menu = MenuCode.WORK_ORDER, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteWorkOrders(@RequestBody List<Long> ids) {
        workOrderService.deleteWorkOrders(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}

package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.work.order.dto.WorkOrderCreateDto;
import com.bizplus.mes.domain.work.order.dto.WorkOrderSearchDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/work-orders")
@RequiredArgsConstructor
public class WorkOrderController {

    private final MessageService messageService;
    private final WorkOrderService workOrderService;

    @GetMapping
    @PreAuthorize("hasAuthority('WORK_ORDER_READ')")
    @UserAction(menu = MenuCode.WORK_ORDER, type = ActionType.READ)
    public String viewList(Model model,
                           WorkOrderSearchDto dto,
                           @PageableDefault Pageable pageable) {
        model.addAttribute("workOrderStatuses", WorkOrderListType.ACTIVE.getStatuses());
        model.addAttribute("data", workOrderService.getWorkOrders(WorkOrderListType.ACTIVE, dto, pageable));

        return "pages/work-order/list";
    }

    @GetMapping("/completed")
    @PreAuthorize("hasAuthority('WORK_ORDER_READ')")
    public String viewCompletedList(Model model,
                                    WorkOrderSearchDto dto,
                                    @PageableDefault Pageable pageable) {
        model.addAttribute("data", workOrderService.getWorkOrders(WorkOrderListType.COMPLETED, dto, pageable));

        return "pages/work-order/completed-list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WORK_ORDER_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("workOrder", workOrderService.getWorkOrder(id));

        return "pages/work-order/detail";
    }

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

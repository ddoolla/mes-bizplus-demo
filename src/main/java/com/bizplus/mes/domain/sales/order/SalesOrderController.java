package com.bizplus.mes.domain.sales.order;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderCreateDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderSearchDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderUpdateDto;
import com.bizplus.mes.domain.sales.order.item.SalesOrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/sales-orders")
@RequiredArgsConstructor
public class SalesOrderController {

    private final MessageService messageService;
    private final SalesOrderService salesOrderService;
    private final SalesOrderItemService salesOrderItemService;

    @GetMapping
    @PreAuthorize("hasAuthority('SALES_ORDER_READ')")
    @UserAction(menu = MenuCode.SALES_ORDER, type = ActionType.READ)
    public String viewList(Model model, SalesOrderSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("salesOrderStatus", SalesOrderStatus.values());
        model.addAttribute("data", salesOrderService.getSalesOrders(dto, pageable));

        return "pages/sales-order/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SALES_ORDER_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("salesOrder", salesOrderService.getSalesOrder(id));
        model.addAttribute("salesOrderItems", salesOrderItemService.getSalesOrderItems(id));

        return "pages/sales-order/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('SALES_ORDER_CREATE')")
    public String viewNew() {
        return "pages/sales-order/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('SALES_ORDER_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("salesOrder", salesOrderService.getSalesOrder(id));
        model.addAttribute("salesOrderItems", salesOrderItemService.getSalesOrderItems(id));

        return "pages/sales-order/edit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SALES_ORDER_CREATE')")
    @UserAction(menu = MenuCode.SALES_ORDER, type = ActionType.CREATE)
    public String createSalesOrder(@Valid SalesOrderCreateDto dto, RedirectAttributes reAtt) {
        Long createdId = salesOrderService.createSalesOrder(dto);

        reAtt.addAttribute("id", createdId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/sales-orders/{id}/edit";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('SALES_ORDER_UPDATE')")
    @UserAction(menu = MenuCode.SALES_ORDER, type = ActionType.UPDATE)
    public String updateSalesOrder(@PathVariable Long id,
                                   @Valid SalesOrderUpdateDto dto,
                                   RedirectAttributes reAtt) {
        salesOrderService.updateSalesOrder(id, dto);

        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/sales-orders";
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('SALES_ORDER_DELETE')")
    @UserAction(menu = MenuCode.SALES_ORDER, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteSalesOrders(@RequestBody List<Long> ids) {
        salesOrderService.deleteSalesOrders(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}

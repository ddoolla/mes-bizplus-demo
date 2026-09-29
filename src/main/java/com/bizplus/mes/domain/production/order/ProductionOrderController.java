package com.bizplus.mes.domain.production.order;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.item.ItemGroup;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderCreateDto;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderSearchDto;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderUpdateDto;
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
@RequestMapping("/production-orders")
@RequiredArgsConstructor
public class ProductionOrderController {

    private final MessageService messageService;
    private final CommonCodeService commonCodeService;
    private final ProductionOrderService productionOrderService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('PRODUCTION_ORDER_READ')")
    @UserAction(menu = MenuCode.PRODUCTION_ORDER, type = ActionType.READ)
    public String viewList(Model model, ProductionOrderSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("productionOrderStatus", ProductionOrderStatus.values());
        model.addAttribute("data", productionOrderService.getProductionOrders(dto, pageable));

        return "pages/production-order/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('PRODUCTION_ORDER_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("productionOrder", productionOrderService.getProductionOrder(id));

        return "pages/production-order/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('PRODUCTION_ORDER_CREATE')")
    public String viewNew(Model model) {
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemGroup.PRODUCT.getTypes());
        return "pages/production-order/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyAuthority('PRODUCTION_ORDER_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("productionOrder", productionOrderService.getProductionOrder(id));

        return "pages/production-order/edit";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('PRODUCTION_ORDER_CREATE')")
    @UserAction(menu = MenuCode.PRODUCTION_ORDER, type = ActionType.CREATE)
    public String createProductionOrder(@Valid ProductionOrderCreateDto dto, RedirectAttributes reAtt) {
        Long createdId = productionOrderService.createProductionOrder(dto);

        reAtt.addAttribute("id", createdId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/production-orders/{id}";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('PRODUCTION_ORDER_UPDATE')")
    @UserAction(menu = MenuCode.PRODUCTION_ORDER, type = ActionType.UPDATE)
    public String updateProductionOrder(@PathVariable Long id,
                                        @Valid ProductionOrderUpdateDto dto,
                                        RedirectAttributes reAtt) {
        productionOrderService.updateProductionOrder(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/production-orders/{id}";
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('PRODUCTION_ORDER_DELETE')")
    @UserAction(menu = MenuCode.PRODUCTION_ORDER, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteProductionOrders(@RequestBody List<Long> ids) {
        productionOrderService.deleteProductionOrders(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}

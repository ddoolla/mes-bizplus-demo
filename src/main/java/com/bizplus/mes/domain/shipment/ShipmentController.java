package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.item.ItemGroup;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.shipment.dto.ShipmentCreateDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentUpdateDto;
import com.bizplus.mes.domain.shipment.facade.ShipmentUpdateService;
import com.bizplus.mes.domain.shipment.item.ShipmentItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final MessageService messageService;
    private final CommonCodeService commonCodeService;
    private final ShipmentService shipmentService;
    private final ShipmentItemService shipmentItemService;

    private final ShipmentUpdateService shipmentUpdateService;

    @GetMapping
    @PreAuthorize("hasAuthority('SHIPMENT_READ')")
    @UserAction(menu = MenuCode.SHIPMENT, type = ActionType.READ)
    public String viewList(Model model, ShipmentSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", shipmentService.getAll(dto, pageable));

        return "pages/shipment/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SHIPMENT_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("shipment", shipmentService.getById(id));
        model.addAttribute("shipmentItems", shipmentItemService.getAll(id));
        model.addAttribute(
                "totalAmount",
                shipmentItemService.getAll(id).stream().map(item -> {
                            BigDecimal quantity = item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO;
                            BigDecimal unitPrice = item.getUnitPrice() != null ? item.getUnitPrice() : BigDecimal.ZERO;

                            return quantity.multiply(unitPrice);
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return "pages/shipment/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('SHIPMENT_CREATE')")
    public String viewNew() {
        return "pages/shipment/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('SHIPMENT_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("shipment", shipmentService.getById(id));
        model.addAttribute("shipmentItems", shipmentItemService.getAll(id));
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemGroup.PRODUCT.getTypes());

        return "pages/shipment/edit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SHIPMENT_CREATE')")
    @UserAction(menu = MenuCode.SHIPMENT, type = ActionType.CREATE)
    public String create(@Valid ShipmentCreateDto dto, RedirectAttributes reAtt) {
        Long createdId = shipmentService.create(dto);

        reAtt.addAttribute("id", createdId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/shipments/{id}";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('SHIPMENT_UPDATE')")
    @UserAction(menu = MenuCode.SHIPMENT, type = ActionType.UPDATE)
    public String update(@PathVariable Long id, @Valid ShipmentUpdateDto dto, RedirectAttributes reAtt) {
        shipmentUpdateService.update(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/shipments/{id}";
    }
}

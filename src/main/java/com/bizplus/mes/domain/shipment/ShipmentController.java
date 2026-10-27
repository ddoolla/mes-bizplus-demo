package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.shipment.dto.ShipmentCreateDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
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

@Controller
@RequestMapping("/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final MessageService messageService;
    private final ShipmentService shipmentService;
    private final ShipmentItemService shipmentItemService;

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

        return "pages/shipment/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('SHIPMENT_CREATE')")
    public String viewNew() {
        return "pages/shipment/new";
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
}

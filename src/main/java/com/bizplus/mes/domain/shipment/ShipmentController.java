package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

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
        model.addAttribute("data", shipmentService.getById(id));

        return "pages/shipment/detail";
    }
}

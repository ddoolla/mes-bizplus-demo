package com.bizplus.mes.domain.inventory.adjustment;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentCreateDto;
import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentSearchDto;
import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventory-adjustments")
@RequiredArgsConstructor
public class InventoryAdjustmentController {

    private final MessageService messageService;
    private final CommonCodeService commonCodeService;
    private final InventoryAdjustmentService inventoryAdjustmentService;

    @GetMapping
    @PreAuthorize("hasAuthority('INVENTORY_ADJUSTMENT_READ')")
    @UserAction(menu = MenuCode.INVENTORY_ADJUSTMENT, type = ActionType.READ)
    public String viewList(Model model, InventoryAdjustmentSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", inventoryAdjustmentService.getAll(dto, pageable));

        return "pages/inventory-adjustment/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('INVENTORY_ADJUSTMENT_CREATE')")
    public String viewNew(Model model) {
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemType.values());

        return "pages/inventory-adjustment/new";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INVENTORY_ADJUSTMENT_CREATE')")
    @UserAction(menu = MenuCode.INVENTORY_ADJUSTMENT, type = ActionType.CREATE)
    public String viewList(@Valid InventoryAdjustmentCreateDto dto, RedirectAttributes reAtt) {
        inventoryAdjustmentService.create(dto);

        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/inventory-adjustments";
    }
}

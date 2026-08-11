package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.inventory.dto.ItemInventorySearchDto;
import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final CommonCodeService commonCodeService;

    @GetMapping
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    @UserAction(menu = MenuCode.ITEM_INVENTORY, type = ActionType.READ)
    public String viewList(Model model,
                           ItemInventorySearchDto dto,
                           @PageableDefault Pageable pageable) {

        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("selectedCategory", dto.getCategoryId());
        model.addAttribute("selectedType", dto.getType());
        model.addAttribute("data", inventoryService.getItemInventories(dto, pageable));

        return "pages/inventory/list";
    }
}

package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import com.bizplus.mes.domain.item.ItemService;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final CommonCodeService commonCodeService;
    private final ItemService itemService;
    private final InventoryService inventoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('ITEM_INVENTORY_READ')")
    @UserAction(menu = MenuCode.ITEM_INVENTORY, type = ActionType.READ)
    public String viewList(Model model,
                           InventorySearchDto dto,
                           @PageableDefault Pageable pageable) {

        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("selectedCategory", dto.getCategoryCodeId());
        model.addAttribute("selectedType", dto.getItemType());
        model.addAttribute("data", inventoryService.getAll(dto, pageable));

        return "pages/inventory/list";
    }

    @GetMapping("/modal/detail")
    @PreAuthorize("hasAuthority('ITEM_INVENTORY_READ')")
    public String viewDetailModal(Model model, @RequestParam Long itemId) {
        model.addAttribute("inventories", inventoryService.getAllByItemId(itemId));
        model.addAttribute("item", itemService.getItem(itemId));

        return "pages/inventory/modal/detail :: content";
    }
}

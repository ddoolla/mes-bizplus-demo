package com.bizplus.mes.domain.inventory.transaction;

import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionSearchDto;
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
@RequestMapping("/inventory-transactions")
@RequiredArgsConstructor
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;

    @GetMapping
    @PreAuthorize("hasAuthority('INVENTORY_TRANSACTION_READ')")
    @UserAction(menu = MenuCode.INVENTORY_TRANSACTION, type = ActionType.READ)
    public String viewList(Model model, InventoryTransactionSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", inventoryTransactionService.getAll(dto, pageable));
        model.addAttribute("transactionTypes", TransactionType.values());

        return "pages/inventory-transaction/list";
    }
}

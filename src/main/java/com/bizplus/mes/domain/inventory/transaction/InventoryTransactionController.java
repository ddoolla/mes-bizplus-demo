package com.bizplus.mes.domain.inventory.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;
}

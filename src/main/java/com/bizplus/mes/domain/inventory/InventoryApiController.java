package com.bizplus.mes.domain.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryApiController {

    private final InventoryService inventoryService;
}

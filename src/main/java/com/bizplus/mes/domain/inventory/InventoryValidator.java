package com.bizplus.mes.domain.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryValidator {

    private final InventoryRepository inventoryRepository;
}

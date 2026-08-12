package com.bizplus.mes.domain.inventory.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

    private final InventoryTransactionRepository inventoryTransactionRepository;
}

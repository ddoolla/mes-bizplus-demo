package com.bizplus.mes.domain.inventory.dto;

import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InventoryChangeDto {

    @NotNull
    private Long itemId;
    private Long lotId;

    @NotNull
    private TransactionType type;

    @NotNull
    @Positive
    private BigDecimal quantity;
    private LocalDateTime transactionAt;
}

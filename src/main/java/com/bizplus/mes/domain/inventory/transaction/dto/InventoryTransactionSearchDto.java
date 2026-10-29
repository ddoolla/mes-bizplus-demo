package com.bizplus.mes.domain.inventory.transaction.dto;

import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class InventoryTransactionSearchDto {

    private String item;
    private String lotNo;
    private TransactionType type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}

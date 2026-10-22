package com.bizplus.mes.domain.receipt.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReceiptItemCreateDto {

    @NotNull
    @NotEmpty
    private List<Long> itemIds;
}

package com.bizplus.mes.domain.sales.order.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SalesOrderItemCreateDto {

    @NotBlank
    private List<Long> itemIds;
}

package com.bizplus.mes.domain.sales.order.item.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SalesOrderItemCreateDto {

    @NotEmpty
    private List<Long> itemIds;
}

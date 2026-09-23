package com.bizplus.mes.domain.sales.order.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SalesOrderListDto {

    private List<SalesOrderDto> salesOrders;
    private Pagination pagination;
}

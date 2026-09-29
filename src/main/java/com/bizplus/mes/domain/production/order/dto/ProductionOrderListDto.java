package com.bizplus.mes.domain.production.order.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductionOrderListDto {

    private List<ProductionOrderDto> productionOrders;
    private Pagination pagination;
}

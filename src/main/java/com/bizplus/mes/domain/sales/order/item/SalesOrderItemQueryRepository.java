package com.bizplus.mes.domain.sales.order.item;

import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemDto;

import java.util.List;

public interface SalesOrderItemQueryRepository {

    List<SalesOrderItemDto> findSalesOrderItems(Long salesOrderId);
}

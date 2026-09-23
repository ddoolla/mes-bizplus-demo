package com.bizplus.mes.domain.sales.order.item;

import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemCreateDto;
import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemDto;
import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemUpdateDto;

import java.util.List;

public interface SalesOrderItemService {

    List<SalesOrderItemDto> getSalesOrderItems(Long salesOrderId);

    void createSalesOrderItems(Long salesOrderId, SalesOrderItemCreateDto dto);

    void updateSalesOrderItems(List<SalesOrderItemUpdateDto> dtoList);

    void deleteSalesOrderItems(List<Long> ids);
}

package com.bizplus.mes.domain.sales.order;

import com.bizplus.mes.domain.sales.order.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SalesOrderService {

    SalesOrderListDto getSalesOrders(SalesOrderSearchDto dto, Pageable pageable);

    SalesOrderDto getSalesOrder(Long id);

    Long createSalesOrder(SalesOrderCreateDto dto);

    void updateSalesOrder(Long id, SalesOrderUpdateDto dto);

    void confirmSalesOrder(Long id);

    void deleteSalesOrders(List<Long> ids);
}

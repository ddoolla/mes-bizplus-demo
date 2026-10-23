package com.bizplus.mes.domain.sales.order;

import com.bizplus.mes.domain.sales.order.dto.SalesOrderCreateDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderListDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderSearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SalesOrderService {

    SalesOrderListDto getSalesOrders(SalesOrderSearchDto dto, Pageable pageable);

    SalesOrderDto getSalesOrder(Long id);

    Long createSalesOrder(SalesOrderCreateDto dto);

    void confirmSalesOrder(Long id);

    void deleteSalesOrders(List<Long> ids);
}

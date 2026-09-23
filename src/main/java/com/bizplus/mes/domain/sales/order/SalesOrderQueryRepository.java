package com.bizplus.mes.domain.sales.order;

import com.bizplus.mes.domain.sales.order.dto.SalesOrderDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface SalesOrderQueryRepository {

    Page<SalesOrderDto> findSalesOrders(SalesOrderSearchDto dto, Pageable pageable);

    Optional<SalesOrderDto> findSalesOrder(Long id);

    String findMaxOrderNo(LocalDate date);
}

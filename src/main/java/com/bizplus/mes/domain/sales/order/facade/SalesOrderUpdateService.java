package com.bizplus.mes.domain.sales.order.facade;

import com.bizplus.mes.domain.sales.order.SalesOrderService;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderUpdateDto;
import com.bizplus.mes.domain.sales.order.item.SalesOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SalesOrderUpdateService {

    private final SalesOrderService salesOrderService;
    private final SalesOrderItemService salesOrderItemService;

    @Transactional
    public void update(Long id, SalesOrderUpdateDto dto) {
        // 수주 정보 업데이트
        salesOrderService.updateSalesOrder(id, dto);

        // 수주 품목 정보 업데이트
        salesOrderItemService.updateSalesOrderItems(dto.getSalesOrderItems());
    }
}

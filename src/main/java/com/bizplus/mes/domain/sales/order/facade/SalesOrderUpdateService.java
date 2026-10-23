package com.bizplus.mes.domain.sales.order.facade;

import com.bizplus.mes.domain.sales.order.SalesOrder;
import com.bizplus.mes.domain.sales.order.SalesOrderReader;
import com.bizplus.mes.domain.sales.order.SalesOrderStatus;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderUpdateDto;
import com.bizplus.mes.domain.sales.order.item.SalesOrderItem;
import com.bizplus.mes.domain.sales.order.item.SalesOrderItemReader;
import com.bizplus.mes.domain.sales.order.item.SalesOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SalesOrderUpdateService {

    private final SalesOrderReader salesOrderReader;
    private final SalesOrderItemReader salesOrderItemReader;

    private final SalesOrderItemService salesOrderItemService;

    @Transactional
    public void update(Long id, SalesOrderUpdateDto dto) {
        SalesOrder salesOrder = salesOrderReader.getById(id);

        // 완료된 수주일 경우 비고만 수정 가능
        if (salesOrder.getStatus() == SalesOrderStatus.COMPLETED) {
            salesOrder.updateRemark(dto.getRemark());

            dto.getSalesOrderItems().forEach(salesOrderItemDto -> {
                SalesOrderItem orderItem = salesOrderItemReader.getById(salesOrderItemDto.getId());
                orderItem.updateRemark(salesOrderItemDto.getRemark());
            });

            return;
        }

        // 수주 정보 업데이트
        salesOrder.update(
                dto.getDate(),
                dto.getDueDate(),
                dto.getRemark()
        );

        // 수주 품목 정보 업데이트
        salesOrderItemService.updateSalesOrderItems(dto.getSalesOrderItems());
    }
}

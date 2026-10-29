package com.bizplus.mes.domain.shipment.facade;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.domain.inventory.InventoryManager;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.sales.order.SalesOrder;
import com.bizplus.mes.domain.sales.order.SalesOrderStatus;
import com.bizplus.mes.domain.sales.order.item.SalesOrderItem;
import com.bizplus.mes.domain.sales.order.item.SalesOrderItemReader;
import com.bizplus.mes.domain.sales.order.item.SalesOrderItemStatus;
import com.bizplus.mes.domain.shipment.Shipment;
import com.bizplus.mes.domain.shipment.ShipmentReader;
import com.bizplus.mes.domain.shipment.ShipmentStatus;
import com.bizplus.mes.domain.shipment.item.ShipmentItemService;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShipmentCompleteService {

    private final ShipmentReader shipmentReader;
    private final SalesOrderItemReader salesOrderItemReader;

    private final InventoryManager inventoryManager;

    private final ShipmentItemService shipmentItemService;

    @Transactional
    public void complete(Long id) {
        Shipment shipment = shipmentReader.getById(id);

        if (shipment.getStatus() == ShipmentStatus.COMPLETED) {
            throw new BusinessException("이미 완료된 출고입니다.");
        }

        List<ShipmentItemDto> shipmentItems = shipmentItemService.getAll(id);

        Map<Long, BigDecimal> shipmentQuantityMap = new HashMap<>();

        shipmentItems.forEach(shipmentItem -> {
            // 재고 차감
            inventoryManager.changeStock(new InventoryChangeDto(
                    shipmentItem.getItem().id(),
                    shipmentItem.getLot() != null
                            ? shipmentItem.getLot().id()
                            : null,
                    TransactionType.SHIPMENT,
                    shipmentItem.getQuantity(),
                    shipment.getDate().atTime(LocalTime.now())
            ));

            // 제품별 출고 수량 집계
            shipmentQuantityMap.merge(
                    shipmentItem.getItem().id(),
                    shipmentItem.getQuantity(),
                    BigDecimal::add
            );
        });

        // 수주 있을 경우 수주 상태 변경
        SalesOrder salesOrder = shipment.getSalesOrder();

        if (salesOrder != null) {
            List<SalesOrderItem> salesOrderItems = salesOrderItemReader.getAll(salesOrder.getId());

            if (!salesOrderItems.isEmpty()) {
                salesOrderItems.forEach(salesOrderItem -> {
                    BigDecimal shipmentQty = shipmentQuantityMap.getOrDefault(
                            salesOrderItem.getItem().getId(),
                            BigDecimal.ZERO
                    );

                    if (shipmentQty.signum() == 0) {
                        return;
                    }

                    BigDecimal totalShippedQty = salesOrderItem.getShippedQuantity().add(shipmentQty);

                    salesOrderItem.updateShippedQuantity(totalShippedQty);

                    if (totalShippedQty.compareTo(salesOrderItem.getQuantity()) >= 0) {
                        salesOrderItem.updateStatus(SalesOrderItemStatus.SHIPPED);
                    } else {
                        salesOrderItem.updateStatus(SalesOrderItemStatus.PARTIALLY_SHIPPED);
                    }
                });

                boolean allShipped = salesOrderItems.stream()
                        .allMatch(item -> item.getShippedQuantity().compareTo(item.getQuantity()) >= 0);

                boolean anyShipped = salesOrderItems.stream()
                        .anyMatch(item -> item.getShippedQuantity().signum() > 0);

                if (allShipped) {
                    salesOrder.updateStatus(SalesOrderStatus.COMPLETED);

                } else if (anyShipped) {
                    salesOrder.updateStatus(SalesOrderStatus.PARTIALLY_SHIPPED);
                }
            }
        }

        // 출하 상태 변경
        shipment.complete();
    }
}

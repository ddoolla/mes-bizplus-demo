package com.bizplus.mes.domain.sales.order.item;

import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.sales.order.SalesOrder;
import com.bizplus.mes.domain.sales.order.SalesOrderReader;
import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemCreateDto;
import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemDto;
import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemUpdateDto;
import com.bizplus.mes.domain.uom.Uom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesOrderItemServiceImpl implements SalesOrderItemService {

    private final SalesOrderItemRepository salesOrderItemRepository;

    private final ItemReader itemReader;
    private final SalesOrderReader salesOrderReader;
    private final SalesOrderItemReader salesOrderItemReader;

    @Override
    public List<SalesOrderItemDto> getSalesOrderItems(Long salesOrderId) {
        return salesOrderItemRepository.fetchSalesOrderItems(salesOrderId);
    }

    @Transactional
    @Override
    public void createSalesOrderItems(Long salesOrderId, SalesOrderItemCreateDto dto) {
        SalesOrder salesOrder = salesOrderReader.getById(salesOrderId);

        dto.getItemIds().forEach(itemId -> {
            Item item = itemReader.getById(itemId);
            Uom uom = item.getUom();

            salesOrderItemRepository.save(SalesOrderItemMapper.toEntity(
                    salesOrder,
                    item,
                    uom,
                    SalesOrderItemStatus.NOT_SHIPPED
            ));
        });
    }

    @Transactional
    @Override
    public void updateSalesOrderItems(List<SalesOrderItemUpdateDto> dtoList) {
        dtoList.forEach(dto -> {
            SalesOrderItem orderItem = salesOrderItemReader.getById(dto.getId());

            BigDecimal quantity = dto.getQuantity() != null
                    ? dto.getQuantity()
                    : BigDecimal.ZERO;

            BigDecimal unitPrice = dto.getUnitPrice() != null
                    ? dto.getUnitPrice()
                    : BigDecimal.ZERO;

            BigDecimal amount = quantity.multiply(unitPrice);

            orderItem.update(
                    dto.getUnitPrice(),
                    dto.getQuantity(),
                    amount,
                    dto.getRemark()
            );
        });

    }

    @Transactional
    @Override
    public void deleteSalesOrderItems(List<Long> ids) {
        ids.forEach(id -> salesOrderItemReader.getById(id).delete());
    }
}

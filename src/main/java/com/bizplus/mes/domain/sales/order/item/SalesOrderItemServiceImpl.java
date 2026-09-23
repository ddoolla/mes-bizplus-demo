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
        return salesOrderItemRepository.findSalesOrderItems(salesOrderId);
    }

    @Transactional
    @Override
    public void createSalesOrderItems(Long salesOrderId, SalesOrderItemCreateDto dto) {
        SalesOrder salesOrder = salesOrderReader.getById(salesOrderId);

        dto.getItemIds().forEach(itemId -> {
            Item item = itemReader.getById(itemId);
            Uom uom = item.getUom();

            salesOrderItemRepository.save(new SalesOrderItem(
                    salesOrder,
                    item,
                    uom,
                    item.getUnitPrice() != null
                            ? item.getUnitPrice()
                            : BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    SalesOrderItemStatus.NOT_SHIPPED,
                    ""
            ));
        });
    }

    @Override
    public void updateSalesOrderItems(List<SalesOrderItemUpdateDto> dtoList) {

    }

    @Override
    public void deleteSalesOrderItems(List<Long> ids) {

    }
}

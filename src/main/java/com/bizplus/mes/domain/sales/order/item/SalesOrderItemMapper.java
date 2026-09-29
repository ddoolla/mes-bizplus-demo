package com.bizplus.mes.domain.sales.order.item;

import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.sales.order.SalesOrder;
import com.bizplus.mes.domain.uom.Uom;

public class SalesOrderItemMapper {

    public static SalesOrderItem toEntity(SalesOrder salesOrder,
                                          Item item,
                                          Uom uom,
                                          SalesOrderItemStatus status) {
        return new SalesOrderItem(
                salesOrder,
                item,
                uom,
                item.getUnitPrice(),
                null,
                null,
                null,
                null,
                status,
                ""
        );
    }
}

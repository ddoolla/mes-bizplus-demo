package com.bizplus.mes.domain.sales.order;

import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderCreateDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderUpdateDto;

public class SalesOrderMapper {

    public static SalesOrder toEntity(Partner partner,
                                      String nextCode,
                                      SalesOrderStatus status,
                                      SalesOrderCreateDto dto) {
        return new SalesOrder(
                partner,
                nextCode,
                dto.getDate(),
                dto.getDueDate(),
                dto.getRemark(),
                status
        );
    }

    public static void apply(SalesOrder salesOrder, SalesOrderUpdateDto dto) {
        salesOrder.update(
                dto.getDate(),
                dto.getDueDate(),
                dto.getRemark()
        );
    }
}

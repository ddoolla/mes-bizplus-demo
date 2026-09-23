package com.bizplus.mes.domain.sales.order.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalesOrderItemReader {

    private final SalesOrderItemRepository salesOrderItemRepository;

    public SalesOrderItem getById(Long id) {
        return salesOrderItemRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SALES_ORDER_ITEM_NOT_FOUND, "id: " + id));
    }
}

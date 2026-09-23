package com.bizplus.mes.domain.sales.order;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalesOrderReader {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrder getById(Long id) {
        return salesOrderRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SALES_ORDER_NOT_FOUND, "id: " + id));
    }
}

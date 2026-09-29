package com.bizplus.mes.domain.production.order;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductionOrderReader {

    private final ProductionOrderRepository productionOrderRepository;

    public ProductionOrder getById(Long id) {
        return productionOrderRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCTION_ORDER_NOT_FOUND, "id:" + id));
    }
}

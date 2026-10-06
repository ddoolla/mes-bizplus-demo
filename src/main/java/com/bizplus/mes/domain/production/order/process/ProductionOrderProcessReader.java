package com.bizplus.mes.domain.production.order.process;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductionOrderProcessReader {

    private final ProductionOrderProcessRepository productionOrderProcessRepository;

    public ProductionOrderProcess getById(Long id) {
        return productionOrderProcessRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCTION_ORDER_PROCESS_NOT_FOUND, "id: " + id) );
    }
}

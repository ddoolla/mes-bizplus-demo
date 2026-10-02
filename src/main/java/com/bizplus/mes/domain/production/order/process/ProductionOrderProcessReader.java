package com.bizplus.mes.domain.production.order.process;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductionOrderProcessReader {

    private final ProductionOrderProcessRepository productionOrderProcessRepository;

    public List<ProductionOrderProcess> getByProductionOrderId(Long productionOrderId) {
        return productionOrderProcessRepository.findByProductionOrderIdAndDeletedAtIsNull(productionOrderId);
    }
}

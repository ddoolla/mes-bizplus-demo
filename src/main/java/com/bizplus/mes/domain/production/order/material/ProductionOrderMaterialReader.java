package com.bizplus.mes.domain.production.order.material;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductionOrderMaterialReader {

    private final ProductionOrderMaterialRepository productionOrderMaterialRepository;
}

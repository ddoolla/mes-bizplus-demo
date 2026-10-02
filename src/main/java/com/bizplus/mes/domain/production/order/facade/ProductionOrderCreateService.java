package com.bizplus.mes.domain.production.order.facade;

import com.bizplus.mes.domain.production.order.ProductionOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductionOrderCreateService {

    private final ProductionOrderService productionOrderService;
}

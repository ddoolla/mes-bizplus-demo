package com.bizplus.mes.domain.production.order;

import com.bizplus.mes.domain.production.order.dto.ProductionOrderDto;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductionOrderQueryRepository {

    Page<ProductionOrderDto> fetchProductionOrders(ProductionOrderSearchDto dto, Pageable pageable);

    Optional<ProductionOrderDto> findProductionOrder(Long id);

    Optional<ProductionOrder> findProductionOrderForUpdate(Long id);
}

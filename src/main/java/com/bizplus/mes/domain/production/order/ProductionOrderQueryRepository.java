package com.bizplus.mes.domain.production.order;

import com.bizplus.mes.domain.production.order.dto.ProductionOrderDto;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface ProductionOrderQueryRepository {

    Page<ProductionOrderDto> findProductionOrders(ProductionOrderSearchDto dto, Pageable pageable);

    Optional<ProductionOrderDto> findProductionOrder(Long id);

    String findMaxOrderNo(LocalDate date);
}

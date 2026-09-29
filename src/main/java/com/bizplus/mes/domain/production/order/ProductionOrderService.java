package com.bizplus.mes.domain.production.order;

import com.bizplus.mes.domain.production.order.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductionOrderService {

    ProductionOrderListDto getProductionOrders(ProductionOrderSearchDto dto, Pageable pageable);

    ProductionOrderDto getProductionOrder(Long id);

    Long createProductionOrder(ProductionOrderCreateDto dto);

    void updateProductionOrder(Long id, ProductionOrderUpdateDto dto);

    void deleteProductionOrders(List<Long> ids);
}

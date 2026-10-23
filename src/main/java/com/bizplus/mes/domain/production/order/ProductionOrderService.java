package com.bizplus.mes.domain.production.order;

import com.bizplus.mes.domain.production.order.dto.ProductionOrderCreateDto;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderDto;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderListDto;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderSearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductionOrderService {

    ProductionOrderListDto getProductionOrders(ProductionOrderSearchDto dto, Pageable pageable);

    ProductionOrderDto getProductionOrder(Long id);

    Long createProductionOrder(ProductionOrderCreateDto dto);

    void confirmProductionOrder(Long id, Long lotId);

    void startProduction(Long id);

    void completeProduction(Long id);

    void deleteProductionOrders(List<Long> ids);
}

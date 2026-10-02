package com.bizplus.mes.domain.production.order;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.common.util.CodeGenerator;
import com.bizplus.mes.common.util.CodePrefix;
import com.bizplus.mes.domain.bom.Bom;
import com.bizplus.mes.domain.bom.BomReader;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.production.order.dto.*;
import com.bizplus.mes.domain.routing.Routing;
import com.bizplus.mes.domain.routing.RoutingReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionOrderServiceImpl implements ProductionOrderService {

    private final ProductionOrderRepository productionOrderRepository;

    private final ItemReader itemReader;
    private final BomReader bomReader;
    private final RoutingReader routingReader;
    private final ProductionOrderReader productionOrderReader;

    @Override
    public ProductionOrderListDto getProductionOrders(ProductionOrderSearchDto dto, Pageable pageable) {
        Page<ProductionOrderDto> productionOrderPage = productionOrderRepository.findProductionOrders(dto, pageable);

        return new ProductionOrderListDto(
                productionOrderPage.getContent(),
                Pagination.of(productionOrderPage)
        );
    }

    @Override
    public ProductionOrderDto getProductionOrder(Long id) {
        return productionOrderRepository.findProductionOrder(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCTION_ORDER_NOT_FOUND, "id:" + id));
    }

    @Override
    public Long createProductionOrder(ProductionOrderCreateDto dto) {
        Item item = itemReader.getById(dto.getItemId());
        Bom bom = bomReader.getById(dto.getBomId());
        Routing routing = routingReader.getById(dto.getRoutingId());

        LocalDate today = LocalDate.now();
        String maxOrderNo = productionOrderRepository.findMaxOrderNo(today);

        return productionOrderRepository.save(new ProductionOrder(
                        item,
                        bom,
                        routing,
                        CodeGenerator.generate(
                                CodePrefix.PRODUCTION_ORDER,
                                today,
                                maxOrderNo
                        ),
                        dto.getQuantity(),
                        dto.getDueDate(),
                        ProductionOrderStatus.DRAFT,
                        dto.getRemark()
                ))
                .getId();
    }

    @Transactional
    @Override
    public void updateProductionOrder(Long id, ProductionOrderUpdateDto dto) {
        ProductionOrder productionOrder = productionOrderReader.getById(id);

        productionOrder.update(
                dto.getQuantity(),
                dto.getDueDate(),
                dto.getRemark()
        );
    }

    @Transactional
    @Override
    public void deleteProductionOrders(List<Long> ids) {
        ids.forEach(id -> productionOrderReader.getById(id).delete());
    }
}

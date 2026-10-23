package com.bizplus.mes.domain.production.result;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.production.order.ProductionOrderReader;
import com.bizplus.mes.domain.production.result.dto.ProductionResultCreateDto;
import com.bizplus.mes.domain.production.result.dto.ProductionResultDto;
import com.bizplus.mes.domain.production.result.dto.ProductionResultListDto;
import com.bizplus.mes.domain.production.result.dto.ProductionResultSearchDto;
import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductionResultServiceImpl implements ProductionResultService {

    private final ProductionResultRepository productionResultRepository;

    private final ProductionOrderReader productionOrderReader;
    private final WorkOrderReader workOrderReader;

    @Override
    public ProductionResultListDto getAll(ProductionResultSearchDto dto, Pageable pageable) {
        Page<ProductionResultDto> page = productionResultRepository.fetchProductionResults(dto, pageable);

        return new ProductionResultListDto(page.getContent(), Pagination.of(page));
    }

    @Override
    public void create(ProductionResultCreateDto dto) {
        ProductionOrder productionOrder = productionOrderReader.getById(dto.getProductionOrderId());
        WorkOrder workOrder = workOrderReader.getById(dto.getWorkOrderId());

        productionResultRepository.save(new ProductionResult(
                productionOrder,
                workOrder,
                dto.getTotalQuantity(),
                dto.getGoodQuantity(),
                dto.getDefectQuantity(),
                dto.getCompletedAt()
        ));
    }
}

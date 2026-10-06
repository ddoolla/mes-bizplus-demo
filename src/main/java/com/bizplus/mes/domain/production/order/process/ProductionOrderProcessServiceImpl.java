package com.bizplus.mes.domain.production.order.process;

import com.bizplus.mes.domain.process.Process;
import com.bizplus.mes.domain.process.ProcessReader;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.production.order.ProductionOrderReader;
import com.bizplus.mes.domain.production.order.process.dto.ProductionOrderProcessDto;
import com.bizplus.mes.domain.routing.process.RoutingProcess;
import com.bizplus.mes.domain.routing.process.RoutingProcessReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionOrderProcessServiceImpl implements ProductionOrderProcessService {

    private final ProductionOrderProcessRepository productionOrderProcessRepository;

    private final ProcessReader processReader;
    private final RoutingProcessReader routingProcessReader;
    private final ProductionOrderReader productionOrderReader;

    @Override
    public List<ProductionOrderProcessDto> getProductionOrderProcesses(Long productionOrderId) {
        return productionOrderProcessRepository.findByProductionOrderIdAndDeletedAtIsNull(productionOrderId)
                .stream()
                .map(pop ->
                        new ProductionOrderProcessDto(
                                pop.getId(),
                                pop.getCode(),
                                pop.getName(),
                                pop.getStepNo()
                        )
                )
                .toList();
    }

    @Transactional
    @Override
    public List<Long> createProductionOrderProcesses(Long productionOrderId) {
        ProductionOrder productionOrder = productionOrderReader.getById(productionOrderId);
        List<RoutingProcess> routingProcesses = routingProcessReader
                .getByRoutingId(productionOrder.getRouting().getId());

        return routingProcesses.stream().map(rp -> {
                    Process process = processReader.getById(rp.getProcess().getId());

                    return productionOrderProcessRepository.save(new ProductionOrderProcess(
                                    productionOrder,
                                    process,
                                    process.getCode(),
                                    process.getName(),
                                    rp.getStepNo()
                            ))
                            .getId();
                })
                .toList();
    }
}

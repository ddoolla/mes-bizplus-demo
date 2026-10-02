package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.common.util.CodeGenerator;
import com.bizplus.mes.common.util.CodePrefix;
import com.bizplus.mes.domain.equipment.EquipmentReader;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.production.order.ProductionOrderReader;
import com.bizplus.mes.domain.production.order.process.ProductionOrderProcess;
import com.bizplus.mes.domain.production.order.process.ProductionOrderProcessReader;
import com.bizplus.mes.domain.work.order.dto.WorkOrderCreateDto;
import com.bizplus.mes.domain.worker.WorkerReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkOrderRepository workOrderRepository;

    private final EquipmentReader equipmentReader;
    private final WorkerReader workerReader;
    private final ProductionOrderReader productionOrderReader;
    private final ProductionOrderProcessReader productionOrderProcessReader;

    @Transactional
    @Override
    public void createWorkOrders(WorkOrderCreateDto dto) {
        List<Long> popIds = dto.getProductionOrderProcessId();
        LocalDate today = LocalDate.now();

        // todo 수량을 어떻게 해야할지 ... 처음 생산은 수량 주고, 추가 생산은 검증 후 0으로 ??..
        popIds.forEach(popId -> {
            ProductionOrderProcess pop = productionOrderProcessReader.getById(popId);
            ProductionOrder productionOrder = productionOrderReader.getById(pop.getProductionOrder().getId());

            String maxOrderNo = workOrderRepository.findMaxOrderNo(today);

            workOrderRepository.save(new WorkOrder(
                    pop,
                    null,
                    null,
                    CodeGenerator.generate(CodePrefix.WORK_ORDER, today, maxOrderNo),
                    productionOrder.getQuantity(),
                    WorkOrderStatus.PENDING,
                    null,
                    null
            ));
        });
    }
}

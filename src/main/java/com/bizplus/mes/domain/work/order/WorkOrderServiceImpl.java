package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.common.util.CodeGenerator;
import com.bizplus.mes.common.util.CodePrefix;
import com.bizplus.mes.domain.equipment.Equipment;
import com.bizplus.mes.domain.equipment.EquipmentReader;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.production.order.ProductionOrderReader;
import com.bizplus.mes.domain.production.order.process.ProductionOrderProcess;
import com.bizplus.mes.domain.production.order.process.ProductionOrderProcessReader;
import com.bizplus.mes.domain.work.order.dto.*;
import com.bizplus.mes.domain.worker.Worker;
import com.bizplus.mes.domain.worker.WorkerReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final WorkOrderReader workOrderReader;

    @Override
    public WorkOrderListDto getWorkOrders(WorkOrderSearchDto dto, Pageable pageable) {
        Page<WorkOrderDto> workOrderPage = workOrderRepository.findWorkOrders(dto, pageable);

        return new WorkOrderListDto(workOrderPage.getContent(), Pagination.of(workOrderPage));
    }

    @Override
    public List<WorkOrderDto> getWorkOrders(Long ProductionOrderId) {
        return workOrderRepository.findWorkOrders(ProductionOrderId);
    }

    @Override
    public WorkOrderDto getWorkOrder(Long id) {
        return workOrderRepository.findWorkOrder(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORK_ORDER_NOT_FOUND, "id: " + id));
    }

    @Transactional
    @Override
    public void autoCreateWorkOrders(Long productionOrderId, WorkOrderCreateDto dto) {
        List<Long> popIds = dto.getProductionOrderProcessIds();
        LocalDate today = LocalDate.now();

        ProductionOrder productionOrder = productionOrderReader.getById(productionOrderId);

        // todo 일단 생산지시 생성 시, 기본적으로 하나씩 생성해 주고, 추가하는 방향으로
        popIds.forEach(popId -> {
            ProductionOrderProcess pop = productionOrderProcessReader.getById(popId);

            String maxOrderNo = workOrderRepository.findMaxOrderNo(today);

            // todo 이미 생성된 작업지시가 있다면, 수량 비교 후 quantity 설정 또는 null 설정

            workOrderRepository.save(new WorkOrder(
                    pop,
                    null,
                    null,
                    CodeGenerator.generate(CodePrefix.WORK_ORDER, today, maxOrderNo),
                    productionOrder.getQuantity(),
                    productionOrder.getDueDate(), // 일단 생산 예정일로 생성
                    WorkOrderStatus.DRAFT,
                    null,
                    null,
                    null
            ));
        });
    }

    @Transactional
    @Override
    public void createWorkOrders(WorkOrderCreateDto dto) {
        LocalDate today = LocalDate.now();

        dto.getProductionOrderProcessIds().forEach(popId -> {
            ProductionOrderProcess pop = productionOrderProcessReader.getById(popId);

            String maxOrderNo = workOrderRepository.findMaxOrderNo(today);

            workOrderRepository.save(new WorkOrder(
                    pop,
                    null,
                    null,
                    CodeGenerator.generate(CodePrefix.WORK_ORDER, today, maxOrderNo),
                    null,
                    null,
                    WorkOrderStatus.DRAFT,
                    null,
                    null,
                    null
            ));
        });
    }

    @Transactional
    @Override
    public void updateWorkOrders(List<WorkOrderUpdateDto> dtoList) {
        dtoList.forEach(dto -> {
            WorkOrder workOrder = workOrderReader.getById(dto.getId());
            Equipment equipment = equipmentReader.getByIdOrNull(dto.getEquipmentId());
            Worker worker = workerReader.getByIdOrNull(dto.getWorkerId());

            workOrder.update(
                    equipment,
                    worker,
                    dto.getQuantity(),
                    dto.getDate(),
                    dto.getRemark()
            );
        });
    }

    @Transactional
    @Override
    public void deleteWorkOrders(List<Long> ids) {
        ids.forEach(id -> workOrderReader.getById(id).delete());
    }
}

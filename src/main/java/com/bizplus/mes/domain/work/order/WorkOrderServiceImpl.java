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
import com.bizplus.mes.domain.user.User;
import com.bizplus.mes.domain.user.UserReader;
import com.bizplus.mes.domain.work.order.dto.*;
import com.bizplus.mes.security.SecurityUtils;
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

    private final UserReader userReader;
    private final EquipmentReader equipmentReader;
    private final ProductionOrderReader productionOrderReader;
    private final ProductionOrderProcessReader productionOrderProcessReader;
    private final WorkOrderReader workOrderReader;

    @Override
    public WorkOrderListDto getWorkOrders(WorkOrderListType listType, WorkOrderSearchDto dto, Pageable pageable) {
        User user = SecurityUtils.getCurrentUser().getUser();

        Page<WorkOrderDto> workOrderPage;

        if ("admin".equals(user.getLoginId())) {
            workOrderPage = workOrderRepository.findWorkOrders(listType, dto, pageable);

        } else {
            workOrderPage = workOrderRepository.findWorkOrdersByUserId(listType, user.getId(), dto, pageable);
        }

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

        popIds.forEach(popId -> {
            ProductionOrderProcess pop = productionOrderProcessReader.getById(popId);

            String maxOrderNo = workOrderRepository.findMaxOrderNo(today);

            workOrderRepository.save(new WorkOrder(
                    pop,
                    null,
                    null,
                    CodeGenerator.generate(CodePrefix.WORK_ORDER, today, maxOrderNo),
                    productionOrder.getQuantity(),
                    productionOrder.getDueDate(), // 일단 생산 예정일로 생성
                    WorkOrderStatus.PENDING,
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
                    WorkOrderStatus.PENDING,
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
            User user = userReader.getByIdOrNull(dto.getUserId());

            workOrder.update(
                    equipment,
                    user,
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

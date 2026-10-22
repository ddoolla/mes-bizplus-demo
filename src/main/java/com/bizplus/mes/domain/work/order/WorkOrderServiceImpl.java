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
import com.bizplus.mes.domain.work.order.event.WorkStartedEvent;
import com.bizplus.mes.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public WorkOrderListDto getWorkOrders(WorkOrderListType listType, WorkOrderSearchDto dto, Pageable pageable) {
        User user = SecurityUtils.getCurrentUser().getUser();

        Page<WorkOrderDto> workOrderPage;

        if ("admin".equals(user.getLoginId())) {
            workOrderPage = workOrderRepository.fetchWorkOrders(listType, dto, pageable);

        } else {
            workOrderPage = workOrderRepository.fetchWorkOrdersByUserId(listType, user.getId(), dto, pageable);
        }

        return new WorkOrderListDto(workOrderPage.getContent(), Pagination.of(workOrderPage));
    }

    @Override
    public List<WorkOrderDto> getWorkOrders(Long ProductionOrderId) {
        return workOrderRepository.fetchWorkOrders(ProductionOrderId);
    }

    @Override
    public WorkOrderDetailDto getWorkOrder(Long id) {
        WorkOrderDto workOrderDto = workOrderRepository.fetchWorkOrder(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORK_ORDER_NOT_FOUND, "id: " + id));

        BigDecimal quantity = workOrderDto.getQuantity();
        BigDecimal productionCount = workOrderDto.getResult().productionQuantity();
        BigDecimal progressRate = BigDecimal.ZERO;

        if (quantity != null && quantity.compareTo(BigDecimal.ZERO) > 0 && productionCount != null) {
            progressRate = productionCount
                    .multiply(BigDecimal.valueOf(100))
                    .divide(quantity, 0, RoundingMode.HALF_UP);
        }

        return new WorkOrderDetailDto(workOrderDto, progressRate);
    }

    @Override
    public boolean isAllCompleted(Long productionOrderId) {
        return !workOrderRepository.existsIncompleteWorkOrder(productionOrderId);
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
                    null
            ));
        });
    }

    @Transactional
    @Override
    public Equipment startWork(Long id) {
        WorkOrder workOrder = workOrderReader.getById(id);

        User assignedUser = workOrder.getUser();
        User currentUser = SecurityUtils.getCurrentUser().getUser();

        if (assignedUser != null
                && !assignedUser.getId().equals(currentUser.getId())) {
            throw new BusinessException("다른 작업자에게 지정된 작업지시입니다.");
        }

        // 작업자가 없는 경우 현재 사용자로 할당
        if (assignedUser == null) {
            workOrder.assignWorker(currentUser);
        }

        workOrder.startWork();

        return workOrder.getEquipment();
    }

    @Transactional
    @Override
    public void stopWork(Long id) {
        WorkOrder workOrder = workOrderReader.getById(id);
        workOrder.stopWork();
    }

    @Transactional
    @Override
    public void resumeWork(Long id) {
        WorkOrder workOrder = workOrderReader.getById(id);

        if (workOrder.getStatus() != WorkOrderStatus.PAUSED) {
            throw new BusinessException("작업 중지 상태에서만 재개 가능합니다.");
        }

        workOrder.resumeWork();

        // PLC 초기화 이벤트 (senderId를 잘 맞춰줘야 함)
        if (workOrder.getEquipment() != null) {
            eventPublisher.publishEvent(new WorkStartedEvent(workOrder.getEquipment().getSenderId()));
        }
    }

    @Transactional
    @Override
    public void completeWork(Long id) {
        WorkOrder workOrder = workOrderReader.getById(id);
        workOrder.completeWork();
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
    public void changeEquipment(Long id, Long equipmentId) {
        WorkOrder workOrder = workOrderReader.getById(id);
        Equipment equipment = equipmentReader.getById(equipmentId);
        workOrder.assignEquipment(equipment);
    }

    @Transactional
    @Override
    public void deleteWorkOrders(List<Long> ids) {
        ids.forEach(id -> workOrderReader.getById(id).delete());
    }
}

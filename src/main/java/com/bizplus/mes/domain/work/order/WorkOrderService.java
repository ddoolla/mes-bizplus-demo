package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.domain.equipment.Equipment;
import com.bizplus.mes.domain.work.order.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkOrderService {

    WorkOrderListDto getWorkOrders(WorkOrderListType listType, WorkOrderSearchDto dto, Pageable pageable);

    List<WorkOrderDto> getWorkOrders(Long ProductionOrderId);

    WorkOrderDetailDto getWorkOrder(Long id);

    boolean isAllCompleted(Long productionOrderId);

    /**
     * 생산지시 등록 시 자동생성용
     */
    void autoCreateWorkOrders(Long productionOrderId, WorkOrderCreateDto dto);

    /**
     * 추가 작업지시 생성용
     */
    void createWorkOrders(WorkOrderCreateDto dto);

    Equipment startWork(Long id);

    void stopWork(Long id);

    void resumeWork(Long id);

    void completeWork(Long id);

    void updateWorkOrders(List<WorkOrderUpdateDto> dtoList);

    void changeEquipment(Long id, Long equipmentId);

    void deleteWorkOrders(List<Long> ids);
}

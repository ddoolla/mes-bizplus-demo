package com.bizplus.mes.domain.work.order.dto;

import com.bizplus.mes.domain.work.order.WorkOrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class WorkOrderDto {

    private final Long id;
    private final String orderNo;
    private final BigDecimal quantity;
    private final WorkOrderStatus status;
    private final LocalDateTime startedAt;
    private final LocalDateTime completedAt;
    private final ProductionOrderProcessInfo productionOrderProcess;
    private final EquipmentInfo equipment;
    private final WorkerInfo worker;

    @QueryProjection
    public WorkOrderDto(Long id,
                        String orderNo,
                        BigDecimal quantity,
                        WorkOrderStatus status,
                        LocalDateTime startedAt,
                        LocalDateTime completedAt,
                        Long popId,
                        String popCode,
                        String popName,
                        Long equipmentId,
                        String equipmentCode,
                        String equipmentName,
                        Long workerId,
                        String workerCode,
                        String workerName) {
        this.id = id;
        this.orderNo = orderNo;
        this.quantity = quantity;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.productionOrderProcess = new ProductionOrderProcessInfo(
                popId,
                popCode,
                popName
        );
        this.equipment = new EquipmentInfo(
                equipmentId,
                equipmentCode,
                equipmentName
        );
        this.worker = new WorkerInfo(
                workerId,
                workerCode,
                workerName
        );
    }

    public record ProductionOrderProcessInfo(
            Long id,
            String code,
            String name
    ) {
    }

    public record EquipmentInfo(
            Long id,
            String code,
            String name
    ) {
    }

    public record WorkerInfo(
            Long id,
            String code,
            String name
    ) {
    }
}

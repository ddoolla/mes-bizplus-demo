package com.bizplus.mes.domain.work.order.dto;

import com.bizplus.mes.domain.work.order.WorkOrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class WorkOrderDto {

    private final Long id;
    private final String orderNo;
    private final BigDecimal quantity;
    private final LocalDate date;
    private final WorkOrderStatus status;
    private final LocalDateTime startedAt;
    private final LocalDateTime completedAt;
    private final String remark;
    private final ProductionOrderProcessInfo productionOrderProcess;
    private final EquipmentInfo equipment;
    private final WorkerInfo worker;
    private final ItemUomInfo uom;

    @QueryProjection
    public WorkOrderDto(Long id,
                        String orderNo,
                        BigDecimal quantity,
                        LocalDate date,
                        WorkOrderStatus status,
                        LocalDateTime startedAt,
                        LocalDateTime completedAt,
                        String remark,
                        Long popId,
                        String popCode,
                        String popName,
                        Long equipmentId,
                        String equipmentCode,
                        String equipmentName,
                        Long workerId,
                        String workerCode,
                        String workerName,
                        String uomCode,
                        Integer uomScale) {
        this.id = id;
        this.orderNo = orderNo;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.remark = remark;
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
        this.uom = new ItemUomInfo(
                uomCode,
                uomScale
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

    public record ItemUomInfo(
            String code,
            Integer scale
    ) {
    }
}

package com.bizplus.mes.domain.work.result.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class WorkResultDto {

    private final Long id;
    private final BigDecimal productionCount;
    private final BigDecimal goodQuantity;
    private final BigDecimal defectQuantity;
    private final LocalDateTime startedAt;
    private final LocalDateTime completedAt;
    private final String remark;
    private final WorkOrderInfo workOrder;
    private final EquipmentInfo equipment;
    private final UserInfo user;

    @QueryProjection
    public WorkResultDto(Long id,
                         BigDecimal productionCount,
                         BigDecimal goodQuantity,
                         BigDecimal defectQuantity,
                         LocalDateTime startedAt,
                         LocalDateTime completedAt,
                         String remark,
                         Long workOrderId,
                         String workOrderNo,
                         Long equipmentId,
                         String equipmentName,
                         Long userId,
                         String userName) {
        this.id = id;
        this.productionCount = productionCount;
        this.goodQuantity = goodQuantity;
        this.defectQuantity = defectQuantity;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.remark = remark;
        this.workOrder = new WorkOrderInfo(workOrderId, workOrderNo);
        this.equipment = new EquipmentInfo(equipmentId, equipmentName);
        this.user = new UserInfo(userId, userName);
    }

    public record WorkOrderInfo(
            Long id,
            String orderNo
    ) {
    }

    public record EquipmentInfo(
            Long id,
            String name
    ) {
    }

    public record UserInfo(
            Long id,
            String name
    ) {
    }
}

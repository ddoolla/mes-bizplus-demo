package com.bizplus.mes.domain.work.result.dto;

import com.bizplus.mes.domain.item.ItemType;
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
    private final ItemInfo item;
    private final ProcessInfo process;
    private final EquipmentInfo equipment;
    private final UserInfo user;
    private final LotInfo lot;

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
                         Long itemId,
                         String itemCode,
                         String itemName,
                         ItemType itemType,
                         String itemSpec,
                         Long itemUomId,
                         String itemUomCode,
                         Integer itemUomScale,
                         Long processId,
                         String processName,
                         Long defaultIpqcSpecId,
                         String defaultIpqcSpecName,
                         Long equipmentId,
                         String equipmentName,
                         Long userId,
                         String userName,
                         Long lotId,
                         String lotNo) {
        this.id = id;
        this.productionCount = productionCount;
        this.goodQuantity = goodQuantity;
        this.defectQuantity = defectQuantity;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.remark = remark;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemType,
                itemSpec,
                new ItemUomInfo(itemUomId, itemUomCode, itemUomScale)
        );
        this.process = new ProcessInfo(
                processId,
                processName,
                defaultIpqcSpecId,
                defaultIpqcSpecName
        );
        this.workOrder = new WorkOrderInfo(
                workOrderId,
                workOrderNo
        );
        this.equipment = new EquipmentInfo(
                equipmentId,
                equipmentName
        );
        this.user = new UserInfo(
                userId,
                userName
        );
        this.lot = new LotInfo(
                lotId,
                lotNo
        );
    }

    public record WorkOrderInfo(Long id, String orderNo) {
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           ItemType type,
                           String specification,
                           ItemUomInfo uom) {
    }

    public record ItemUomInfo(Long id, String code, Integer scale) {
    }

    public record LotInfo(Long id, String no) {
    }

    public record ProcessInfo(Long id,
                              String name,
                              Long defaultIpqcSpecId,
                              String defaultIpqcSpecName) {
    }

    public record EquipmentInfo(Long id, String name) {
    }

    public record UserInfo(Long id, String name) {
    }
}

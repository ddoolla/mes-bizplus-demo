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
    private final ProductionOrderProcessInfo process;
    private final EquipmentInfo equipment;
    private final UserInfo user;
    private final ItemInfo item;
    private final LotInfo lot;

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
                        Long userId,
                        String userName,
                        Long itemId,
                        String itemCode,
                        String itemName,
                        String itemSpec,
                        String uomCode,
                        Integer uomScale,
                        Long lotId,
                        String lotNo) {
        this.id = id;
        this.orderNo = orderNo;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.remark = remark;
        this.process = new ProductionOrderProcessInfo(
                popId,
                popCode,
                popName
        );
        this.equipment = new EquipmentInfo(
                equipmentId,
                equipmentCode,
                equipmentName
        );
        this.user = new UserInfo(
                userId,
                userName
        );
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemSpec,
                new ItemUomInfo(uomCode, uomScale)
        );
        this.lot = new LotInfo(
                lotId,
                lotNo
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

    public record UserInfo(
            Long id,
            String name
    ) {
    }

    public record ItemInfo(
            Long id,
            String code,
            String name,
            String specification,
            ItemUomInfo uom
    ) {
    }

    public record ItemUomInfo(
            String code,
            Integer scale
    ) {
    }

    public record LotInfo(
            Long id,
            String no
    ) {
    }
}

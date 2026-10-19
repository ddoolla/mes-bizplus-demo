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
    private final String remark;
    private final ProductionOrderInfo productionOrder;
    private final ProductionOrderProcessInfo process;
    private final EquipmentInfo equipment;
    private final UserInfo user;
    private final ItemInfo item;
    private final LotInfo lot;
    private final ResultInfo result;

    @QueryProjection
    public WorkOrderDto(Long id,
                        String orderNo,
                        BigDecimal quantity,
                        LocalDate date,
                        WorkOrderStatus status,
                        String remark,
                        Long productionOrderId,
                        String productionOrderNo,
                        Long popId,
                        Long routingProcessId,
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
                        boolean lotManaged,
                        String uomCode,
                        Integer uomScale,
                        Long lotId,
                        String lotNo,
                        BigDecimal productionQuantity,
                        BigDecimal goodQuantity,
                        BigDecimal defectQuantity,
                        LocalDateTime startedAt,
                        LocalDateTime completedAt,
                        String resultRemark) {
        this.id = id;
        this.orderNo = orderNo;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
        this.remark = remark;
        this.productionOrder = new ProductionOrderInfo(
                productionOrderId,
                productionOrderNo
        );
        this.process = new ProductionOrderProcessInfo(
                popId,
                routingProcessId,
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
                lotManaged,
                new ItemUomInfo(uomCode, uomScale)
        );
        this.lot = new LotInfo(
                lotId,
                lotNo
        );
        this.result = new ResultInfo(
                productionQuantity,
                goodQuantity,
                defectQuantity,
                startedAt,
                completedAt,
                resultRemark
        );

    }

    public record ProductionOrderInfo(
            Long id,
            String orderNo
    ) {
    }

    public record ProductionOrderProcessInfo(
            Long id,
            Long routingProcessId,
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
            boolean lotManaged,
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

    public record ResultInfo(
            BigDecimal productionQuantity,
            BigDecimal goodQuantity,
            BigDecimal defectQuantity,
            LocalDateTime startedAt,
            LocalDateTime completedAt,
            String remark
    ) {
    }
}

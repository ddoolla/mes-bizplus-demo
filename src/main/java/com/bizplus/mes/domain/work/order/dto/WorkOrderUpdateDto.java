package com.bizplus.mes.domain.work.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class WorkOrderUpdateDto {

    @NotNull
    private final Long id;
    private final Long equipmentId;
    private final Long userId;

    @NotNull
    @Positive
    private final BigDecimal quantity;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    private final Boolean processInspectionAutoCreate;
    private final String remark;

    public WorkOrderUpdateDto(Long id,
                              Long equipmentId,
                              Long userId,
                              BigDecimal quantity,
                              LocalDate date,
                              Boolean processInspectionAutoCreate,
                              String remark) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.userId = userId;
        this.quantity = quantity;
        this.date = date;
        this.processInspectionAutoCreate = Boolean.TRUE.equals(processInspectionAutoCreate);
        this.remark = remark;
    }
}

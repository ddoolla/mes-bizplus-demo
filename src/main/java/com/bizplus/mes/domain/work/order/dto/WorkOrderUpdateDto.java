package com.bizplus.mes.domain.work.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class WorkOrderUpdateDto {

    @NotNull
    private Long id;
    private Long equipmentId;
    private Long workerId;

    @NotNull
    @Positive
    private BigDecimal quantity;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String remark;
}

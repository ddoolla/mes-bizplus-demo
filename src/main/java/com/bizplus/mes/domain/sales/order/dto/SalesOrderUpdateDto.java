package com.bizplus.mes.domain.sales.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SalesOrderUpdateDto {

    @NotNull
    private Long partnerId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private String remark;
}

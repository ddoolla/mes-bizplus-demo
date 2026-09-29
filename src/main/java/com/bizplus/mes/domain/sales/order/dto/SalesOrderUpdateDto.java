package com.bizplus.mes.domain.sales.order.dto;

import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemUpdateDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SalesOrderUpdateDto {

    @NotNull
    private final Long partnerId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dueDate;
    private final String remark;

    private final List<SalesOrderItemUpdateDto> salesOrderItems;

    public SalesOrderUpdateDto(Long partnerId,
                               LocalDate date,
                               LocalDate dueDate,
                               String remark,
                               List<SalesOrderItemUpdateDto> salesOrderItems) {
        this.partnerId = partnerId;
        this.date = date;
        this.dueDate = dueDate;
        this.remark = remark;
        this.salesOrderItems = salesOrderItems == null ? List.of() : salesOrderItems;
    }
}

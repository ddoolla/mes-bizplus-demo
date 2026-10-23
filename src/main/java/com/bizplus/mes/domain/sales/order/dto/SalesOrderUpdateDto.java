package com.bizplus.mes.domain.sales.order.dto;

import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemUpdateDto;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SalesOrderUpdateDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dueDate;
    private final String remark;

    private final List<SalesOrderItemUpdateDto> salesOrderItems;

    public SalesOrderUpdateDto(LocalDate date,
                               LocalDate dueDate,
                               String remark,
                               List<SalesOrderItemUpdateDto> salesOrderItems) {
        this.date = date;
        this.dueDate = dueDate;
        this.remark = remark;
        this.salesOrderItems = salesOrderItems == null ? List.of() : salesOrderItems;
    }
}

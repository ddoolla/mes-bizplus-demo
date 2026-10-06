package com.bizplus.mes.domain.production.order.dto;

import com.bizplus.mes.domain.work.order.dto.WorkOrderUpdateDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
public class ProductionOrderUpdateDto {

    @NotNull
    @Positive
    private final BigDecimal quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dueDate;
    private final String remark;
    private final List<WorkOrderUpdateDto> workOrders;

    public ProductionOrderUpdateDto(BigDecimal quantity,
                                    LocalDate dueDate,
                                    String remark,
                                    List<WorkOrderUpdateDto> workOrders) {
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.remark = remark;
        this.workOrders = workOrders == null ? List.of() : workOrders;
    }
}

package com.bizplus.mes.domain.work.order.dto;

import com.bizplus.mes.domain.work.order.WorkOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class WorkOrderSearchDto {

    private String item;
    private String process;
    private WorkOrderStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}

package com.bizplus.mes.domain.shipment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ShipmentCreateDto {

    private Long partnerId;
    private Long salesOrderId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String remark;
}

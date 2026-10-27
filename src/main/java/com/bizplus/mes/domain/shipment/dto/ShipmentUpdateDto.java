package com.bizplus.mes.domain.shipment.dto;

import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ShipmentUpdateDto {

    private Long partnerId;
    private Long salesOrderId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String remark;

    private List<ShipmentItemUpdateDto> shipmentItems;
}

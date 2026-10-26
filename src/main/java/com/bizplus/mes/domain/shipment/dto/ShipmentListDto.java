package com.bizplus.mes.domain.shipment.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShipmentListDto {

    private List<ShipmentDto> shipments;
    private Pagination pagination;
}

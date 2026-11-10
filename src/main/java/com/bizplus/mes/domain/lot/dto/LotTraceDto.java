package com.bizplus.mes.domain.lot.dto;

import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionSummaryDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSummaryDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputProductionLotDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemDto;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDetailDto;
import com.bizplus.mes.domain.work.order.dto.WorkOrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LotTraceDto {

    private List<WorkOrderDto> workOrders;
    private List<InProcessInspectionSummaryDto> inProcessInspections;
    private List<MaterialInputDto> materialInputs;
    private ReceiptItemDto receiptItem;
    private IncomingInspectionSummaryDto incomingInspection;
    private List<MaterialInputProductionLotDto> productionLots;
    private List<ShipmentItemDetailDto> shipmentItems;
}

package com.bizplus.mes.domain.lot.facade;

import com.bizplus.mes.domain.inspection.incoming.IncomingInspectionRepository;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionSummaryDto;
import com.bizplus.mes.domain.inspection.inprocess.InProcessInspectionRepository;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSummaryDto;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.lot.LotReader;
import com.bizplus.mes.domain.lot.dto.LotTraceDto;
import com.bizplus.mes.domain.material.input.MaterialInputRepository;
import com.bizplus.mes.domain.material.input.dto.MaterialInputDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputProductionLotDto;
import com.bizplus.mes.domain.receipt.item.ReceiptItemRepository;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemDto;
import com.bizplus.mes.domain.shipment.item.ShipmentItemRepository;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDetailDto;
import com.bizplus.mes.domain.work.order.WorkOrderRepository;
import com.bizplus.mes.domain.work.order.dto.WorkOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotTraceService {

    private final WorkOrderRepository workOrderRepository;
    private final InProcessInspectionRepository inProcessInspectionRepository;
    private final MaterialInputRepository materialInputRepository;
    private final ShipmentItemRepository shipmentItemRepository;

    private final ReceiptItemRepository receiptItemRepository;
    private final IncomingInspectionRepository inspectionRepository;

    private final LotReader lotReader;

    @Transactional
    public LotTraceDto getById(Long lotId) {
        // 로트 기본 정보 조회
        Lot lot = lotReader.getById(lotId);
        Item item = lot.getItem();

        LotTraceDto lotTraceDto = null;

        // 제품일 경우 역방향 추적
        if (item.getType() == ItemType.FINISHED_GOODS
                || item.getType() == ItemType.SEMI_FINISHED_GOODS) {

            // 생산 정보
            List<WorkOrderDto> workOrders = workOrderRepository.fetchAllByLotId(lotId);

            // 공정검사 정보
            List<InProcessInspectionSummaryDto> inProcessInspections = inProcessInspectionRepository.fetchAllSummariesByLotId(lotId);

            // 투입 자재 정보
            List<MaterialInputDto> materialInputs = materialInputRepository.fetchAllByProductionLotId(lotId);

            // 출하 정보
            List<ShipmentItemDetailDto> shipmentItems = shipmentItemRepository.fetchAllByLotId(lotId);

            lotTraceDto = new LotTraceDto(
                    workOrders,
                    inProcessInspections,
                    materialInputs,
                    null,
                    null,
                    null,
                    shipmentItems
            );

            // 자재일 경우 정방향 추적
        } else if (item.getType() == ItemType.RAW_MATERIAL
                || item.getType() == ItemType.SUB_MATERIAL) {
            // 입고 정보
            ReceiptItemDto receiptItem = receiptItemRepository.fetchByLotId(lotId).orElse(null);

            // 입고검사 정보
            IncomingInspectionSummaryDto incomingInspection = inspectionRepository.fetchByLotId(lotId).orElse(null);

            // 생산 투입 정보
            List<MaterialInputDto> materialInputs = materialInputRepository.fetchAllByLotId(lotId);

            // 생산 로트 정보
            List<MaterialInputProductionLotDto> productionLots = materialInputRepository.fetchProductionLots(lotId);

            // 출하 정보
            List<ShipmentItemDetailDto> shipmentItems = null;

            Set<Long> productionLotIds = productionLots.stream()
                    .map(MaterialInputProductionLotDto::getLotId)
                    .collect(Collectors.toSet());

            if (!productionLotIds.isEmpty()) {
                shipmentItems = shipmentItemRepository.fetchAllInLotId(productionLotIds.stream().toList());
            }

            lotTraceDto = new LotTraceDto(
                    null,
                    null,
                    materialInputs,
                    receiptItem,
                    incomingInspection,
                    productionLots,
                    shipmentItems
            );
        }

        return lotTraceDto;
    }
}

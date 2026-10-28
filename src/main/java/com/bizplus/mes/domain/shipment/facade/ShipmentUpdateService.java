package com.bizplus.mes.domain.shipment.facade;

import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.partner.PartnerReader;
import com.bizplus.mes.domain.sales.order.SalesOrder;
import com.bizplus.mes.domain.sales.order.SalesOrderReader;
import com.bizplus.mes.domain.shipment.Shipment;
import com.bizplus.mes.domain.shipment.ShipmentReader;
import com.bizplus.mes.domain.shipment.ShipmentStatus;
import com.bizplus.mes.domain.shipment.dto.ShipmentUpdateDto;
import com.bizplus.mes.domain.shipment.item.ShipmentItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShipmentUpdateService {

    private final PartnerReader partnerReader;
    private final SalesOrderReader salesOrderReader;
    private final ShipmentReader shipmentReader;

    private final ShipmentItemService shipmentItemService;

    @Transactional
    public void update(Long id, ShipmentUpdateDto dto) {
        Shipment shipment = shipmentReader.getById(id);

        // 완료 시 비고만 수정 가능
        if (shipment.getStatus() == ShipmentStatus.COMPLETED) {
            shipment.updateRemark(dto.getRemark());

            return;
        }

        Partner partner = partnerReader.getById(dto.getPartnerId());
        SalesOrder salesOrder = salesOrderReader.getByIdOrNull(dto.getSalesOrderId());

        if (salesOrder != null
                && !Objects.equals(partner.getId(), salesOrder.getPartner().getId())) {
            throw new IllegalArgumentException("[출고 수정] 거래처 ID 불일치 (수주 거래처: %d, 출고 거래처: %d)"
                    .formatted(salesOrder.getPartner().getId(), partner.getId()));
        }

        // 출고 기본 정보 업데이트
        shipment.update(
                partner,
                salesOrder,
                dto.getDate(),
                dto.getRemark()
        );

        // 출고 제품 정보 업데이터
        shipmentItemService.updateAll(dto.getShipmentItems());
    }
}

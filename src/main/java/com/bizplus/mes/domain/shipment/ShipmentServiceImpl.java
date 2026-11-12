package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.partner.PartnerReader;
import com.bizplus.mes.domain.sales.order.SalesOrder;
import com.bizplus.mes.domain.sales.order.SalesOrderReader;
import com.bizplus.mes.domain.sequence.CodeGenerator;
import com.bizplus.mes.domain.sequence.CodePrefix;
import com.bizplus.mes.domain.shipment.dto.ShipmentCreateDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentListDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    private final PartnerReader partnerReader;
    private final SalesOrderReader salesOrderReader;
    private final ShipmentReader shipmentReader;

    private final CodeGenerator codeGenerator;

    @Override
    public ShipmentListDto getAll(ShipmentSearchDto dto, Pageable pageable) {
        Page<ShipmentDto> page = shipmentRepository.fetchShipments(dto, pageable);

        return new ShipmentListDto(page.getContent(), Pagination.of(page));
    }

    @Override
    public ShipmentDto getById(Long id) {
        return shipmentRepository.fetchShipment(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.SHIPMENT_NOT_FOUND,
                        "id: %d".formatted(id)
                ));
    }

    @Override
    public Long create(ShipmentCreateDto dto) {
        Partner partner = partnerReader.getById(dto.getPartnerId());
        SalesOrder salesOrder = salesOrderReader.getByIdOrNull(dto.getSalesOrderId());

        String newShipmentNo = codeGenerator.generate(CodePrefix.SHIPMENT, dto.getDate());

        return shipmentRepository.save(new Shipment(
                        partner,
                        salesOrder,
                        newShipmentNo,
                        dto.getDate(),
                        ShipmentStatus.DRAFT,
                        dto.getRemark()
                ))
                .getId();
    }

    @Transactional
    @Override
    public void deleteAll(List<Long> ids) {
        ids.forEach(id -> shipmentReader.getById(id).delete());
    }
}

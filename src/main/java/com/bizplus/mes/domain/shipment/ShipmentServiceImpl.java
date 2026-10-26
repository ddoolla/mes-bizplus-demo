package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.shipment.dto.ShipmentDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentListDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

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
}

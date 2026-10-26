package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentReader {

    private final ShipmentRepository shipmentRepository;

    public Shipment getById(Long id) {
        return shipmentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.SHIPMENT_NOT_FOUND,
                        "id: %d".formatted(id)
                ));
    }
}

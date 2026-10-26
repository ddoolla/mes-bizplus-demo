package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentItemReader {

    private final ShipmentItemRepository shipmentItemRepository;

    public ShipmentItem getById(Long id) {
        return shipmentItemRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.SHIPMENT_ITEM_NOT_FOUND,
                        "id: %d".formatted(id)
                ));
    }
}

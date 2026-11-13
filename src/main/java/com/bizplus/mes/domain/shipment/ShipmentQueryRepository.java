package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.domain.shipment.dto.ShipmentDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ShipmentQueryRepository {

    Page<ShipmentDto> fetchShipments(ShipmentSearchDto dto, Pageable pageable);

    List<ShipmentDto> fetchAll(ShipmentSearchDto dto);

    Optional<ShipmentDto> fetchShipment(Long id);
}

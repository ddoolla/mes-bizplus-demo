package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.domain.shipment.dto.ShipmentDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface ShipmentQueryRepository {

    Page<ShipmentDto> fetchShipments(ShipmentSearchDto dto, Pageable pageable);

    Optional<ShipmentDto> fetchShipment(Long id);

    String findMaxShipmentNo(LocalDate date);
}

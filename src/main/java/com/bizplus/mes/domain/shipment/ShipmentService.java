package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.domain.shipment.dto.ShipmentCreateDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentListDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShipmentService {

    ShipmentListDto getAll(ShipmentSearchDto dto, Pageable pageable);

    ShipmentDto getById(Long id);

    Long create(ShipmentCreateDto dto);

    void deleteAll(List<Long> ids);
}

package com.bizplus.mes.domain.equipment;

import com.bizplus.mes.domain.equipment.dto.EquipmentDto;
import com.bizplus.mes.domain.equipment.dto.EquipmentSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EquipmentQueryRepository {

    Page<EquipmentDto> findEquipments(EquipmentSearchDto dto, Pageable pageable);

    Optional<EquipmentDto> findEquipment(Long id);
}

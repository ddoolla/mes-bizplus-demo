package com.bizplus.mes.domain.equipment;

import com.bizplus.mes.domain.equipment.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EquipmentService {

    EquipmentListDto getEquipments(EquipmentSearchDto dto, Pageable pageable);

    EquipmentDto getEquipment(Long id);

    boolean checkEquipmentCode(Long id, String code);

    void createEquipment(EquipmentCreateDto dto);

    void updateEquipment(Long id, EquipmentUpdateDto dto);

    void deleteEquipments(List<Long> ids);
}

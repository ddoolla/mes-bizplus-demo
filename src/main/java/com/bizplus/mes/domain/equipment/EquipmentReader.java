package com.bizplus.mes.domain.equipment;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentReader {

    private final EquipmentRepository equipmentRepository;

    public Equipment getById(Long id) {
        return equipmentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.EQUIPMENT_NOT_FOUND, "id: " + id));
    }
}

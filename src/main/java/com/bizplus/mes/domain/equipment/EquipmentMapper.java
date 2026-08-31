package com.bizplus.mes.domain.equipment;

import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.equipment.dto.EquipmentCreateDto;
import com.bizplus.mes.domain.equipment.dto.EquipmentUpdateDto;

public class EquipmentMapper {

    public static Equipment toEntity(CommonCode equipmentTypeCode, EquipmentCreateDto dto) {
        return new Equipment(
                equipmentTypeCode,
                dto.getCode(),
                dto.getName(),
                dto.getSpecification(),
                dto.getManufacturer(),
                dto.getModel(),
                dto.getSerialNo(),
                dto.getLocation(),
                dto.getRemark()
        );
    }

    public static void apply(Equipment equipment, CommonCode equipmentTypeCode, EquipmentUpdateDto dto) {
        equipment.update(
                equipmentTypeCode,
                dto.getCode(),
                dto.getName(),
                dto.getSpecification(),
                dto.getManufacturer(),
                dto.getModel(),
                dto.getSerialNo(),
                dto.getLocation(),
                dto.getRemark()
        );
    }
}

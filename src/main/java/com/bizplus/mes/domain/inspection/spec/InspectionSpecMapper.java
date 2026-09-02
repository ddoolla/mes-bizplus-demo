package com.bizplus.mes.domain.inspection.spec;

import com.bizplus.mes.domain.inspection.spec.dto.InspectionSpecCreateDto;
import com.bizplus.mes.domain.inspection.spec.dto.InspectionSpecUpdateDto;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.process.Process;

public class InspectionSpecMapper {

    public static InspectionSpec toEntity(Item item, Process process, InspectionSpecCreateDto dto) {
        return new InspectionSpec(
                item,
                process,
                dto.getCode(),
                dto.getName(),
                dto.getType(),
                dto.getVersion(),
                dto.isPrimary(),
                dto.getRemark()
        );
    }

    public static void apply(InspectionSpec inspectionSpec, InspectionSpecUpdateDto dto) {
        inspectionSpec.update(
                dto.getCode(),
                dto.getName(),
                dto.getVersion(),
                dto.isPrimary(),
                dto.getRemark()
        );
    }
}

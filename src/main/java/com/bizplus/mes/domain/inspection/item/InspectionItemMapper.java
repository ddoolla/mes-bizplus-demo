package com.bizplus.mes.domain.inspection.item;

import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.inspection.item.dto.InspectionItemCreateDto;
import com.bizplus.mes.domain.inspection.item.dto.InspectionItemUpdateDto;

public class InspectionItemMapper {

    public static InspectionItem toEntity(CommonCode groupCode, InspectionItemCreateDto dto) {
        return new InspectionItem(
                groupCode,
                dto.getCode(),
                dto.getName(),
                dto.getDescription()
        );
    }

    public static void apply(InspectionItem inspectionItem,
                             CommonCode groupCode,
                             InspectionItemUpdateDto dto) {
        inspectionItem.update(
                groupCode,
                dto.getCode(),
                dto.getName(),
                dto.getDescription()
        );
    }
}

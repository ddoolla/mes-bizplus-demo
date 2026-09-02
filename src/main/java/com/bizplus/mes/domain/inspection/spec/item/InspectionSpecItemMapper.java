package com.bizplus.mes.domain.inspection.spec.item;

import com.bizplus.mes.domain.inspection.item.InspectionItem;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemUpdateDto;

public class InspectionSpecItemMapper {

    public static InspectionSpecItem toEntity(InspectionSpec inspectionSpec,
                                              InspectionItem inspectionItem,
                                              Integer nextSortOrder) {
        return new InspectionSpecItem(
                inspectionSpec,
                inspectionItem,
                nextSortOrder,
                "",
                "",
                ""
        );
    }

    public static void apply(InspectionSpecItem inspectionSpecItem,
                             InspectionSpecItemUpdateDto dto) {
        inspectionSpecItem.update(
                dto.getSortOrder(),
                dto.getStandard(),
                dto.getMethod(),
                dto.getRemark()
        );
    }
}

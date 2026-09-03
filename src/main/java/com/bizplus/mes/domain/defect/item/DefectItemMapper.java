package com.bizplus.mes.domain.defect.item;

import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.defect.item.dto.DefectItemCreateDto;
import com.bizplus.mes.domain.defect.item.dto.DefectItemUpdateDto;

public class DefectItemMapper {

    public static DefectItem toEntity(CommonCode defectTypeCode, DefectItemCreateDto dto) {
        return new DefectItem(
                defectTypeCode,
                dto.getCode(),
                dto.getName(),
                dto.getDescription(),
                dto.getRemark()
        );
    }

    public static void apply(DefectItem defectItem,
                             CommonCode defectTypeCode,
                             DefectItemUpdateDto dto) {
        defectItem.update(
                defectTypeCode,
                dto.getCode(),
                dto.getName(),
                dto.getDescription(),
                dto.getRemark()
        );
    }
}

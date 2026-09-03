package com.bizplus.mes.domain.defect.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class DefectItemDto {

    private final Long id;
    private final String code;
    private final String name;
    private final String description;
    private final String remark;
    private final DefectTypeCodeInfo type;

    @QueryProjection
    public DefectItemDto(Long id,
                         String code,
                         String name,
                         String description,
                         String remark,
                         Long typeId,
                         String typeCode,
                         String typeName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.remark = remark;
        this.type = new DefectTypeCodeInfo(typeId, typeCode, typeName);
    }

    public record DefectTypeCodeInfo(
            Long id,
            String code,
            String name
    ) {
    }
}

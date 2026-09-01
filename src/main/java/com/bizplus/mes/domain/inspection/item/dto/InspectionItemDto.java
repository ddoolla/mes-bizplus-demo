package com.bizplus.mes.domain.inspection.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class InspectionItemDto {

    private final Long id;
    private final String code;
    private final String name;
    private final String description;
    private final GroupCodeInfo group;

    @QueryProjection
    public InspectionItemDto(Long id,
                             String code,
                             String name,
                             String description,
                             Long groupId,
                             String groupCode,
                             String groupName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.group = new GroupCodeInfo(groupId, groupCode, groupName);
    }

    public record GroupCodeInfo(
            Long id,
            String code,
            String name
    ) {
    }
}

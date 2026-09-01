package com.bizplus.mes.domain.inspection.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionItemSearchDto {

    private Long groupId;
    private String code;
    private String name;
}

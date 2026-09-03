package com.bizplus.mes.domain.defect.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefectItemSearchDto {

    private Long typeId;
    private String code;
    private String name;
}

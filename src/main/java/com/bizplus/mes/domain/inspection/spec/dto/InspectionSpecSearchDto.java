package com.bizplus.mes.domain.inspection.spec.dto;

import com.bizplus.mes.domain.inspection.spec.InspectionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionSpecSearchDto {

    private String code;
    private String name;
    private InspectionType type;
    private String itemCode;
    private String itemName;
}

package com.bizplus.mes.domain.inspection.spec.dto;

import com.bizplus.mes.domain.inspection.InspectionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionSpecSearchDto {

    private String spec;
    private String item;
    private InspectionType type;
}

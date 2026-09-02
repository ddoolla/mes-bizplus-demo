package com.bizplus.mes.domain.inspection.spec.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionSpecItemUpdateDto {

    @NotNull
    private Long id;
    private Integer sortOrder;
    private String standard;
    private String method;
    private String remark;
}

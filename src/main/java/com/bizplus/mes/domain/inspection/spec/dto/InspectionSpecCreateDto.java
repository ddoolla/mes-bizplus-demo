package com.bizplus.mes.domain.inspection.spec.dto;

import com.bizplus.mes.domain.inspection.InspectionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionSpecCreateDto {

    @NotNull
    private final Long itemId;
    private final Long processId;

    @NotBlank
    private final String code;

    @NotBlank
    private final String name;

    @NotNull
    private final InspectionType type;
    private final String version;
    private final String remark;
    private final Boolean isDefault;
}

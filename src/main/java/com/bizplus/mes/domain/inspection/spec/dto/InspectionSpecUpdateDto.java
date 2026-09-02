package com.bizplus.mes.domain.inspection.spec.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class InspectionSpecUpdateDto {

    @NotBlank
    private final String code;

    @NotBlank
    private final String name;
    private final String version;
    private final boolean primary;
    private final String remark;

    public InspectionSpecUpdateDto(String code,
                                   String name,
                                   String version,
                                   Boolean primary,
                                   String remark) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = Boolean.TRUE.equals(primary);
        this.remark = remark;
    }
}

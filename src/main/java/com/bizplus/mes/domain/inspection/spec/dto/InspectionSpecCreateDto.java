package com.bizplus.mes.domain.inspection.spec.dto;

import com.bizplus.mes.domain.inspection.spec.InspectionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
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
    private final boolean primary;
    private final String remark;

    public InspectionSpecCreateDto(Long itemId,
                                   Long processId,
                                   String code,
                                   String name,
                                   InspectionType type,
                                   String version,
                                   Boolean primary,
                                   String remark) {
        this.itemId = itemId;
        this.processId = processId;
        this.code = code;
        this.name = name;
        this.type = type;
        this.version = version;
        this.primary = Boolean.TRUE.equals(primary);
        this.remark = remark;
    }
}

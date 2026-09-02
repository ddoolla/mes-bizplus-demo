package com.bizplus.mes.domain.inspection.spec.dto;

import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemUpdateDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class InspectionSpecUpdateDto {

    @NotBlank
    private final String code;

    @NotBlank
    private final String name;
    private final String version;
    private final boolean primary;
    private final String remark;

    private final List<InspectionSpecItemUpdateDto> inspectionSpecItems;

    public InspectionSpecUpdateDto(String code,
                                   String name,
                                   String version,
                                   Boolean primary,
                                   String remark,
                                   List<InspectionSpecItemUpdateDto> inspectionSpecItems) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = Boolean.TRUE.equals(primary);
        this.remark = remark;
        this.inspectionSpecItems = inspectionSpecItems == null ? List.of() : inspectionSpecItems;
    }
}

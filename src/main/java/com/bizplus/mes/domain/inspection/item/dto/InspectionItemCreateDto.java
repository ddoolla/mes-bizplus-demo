package com.bizplus.mes.domain.inspection.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionItemCreateDto {

    private Long groupId;

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private String description;
}

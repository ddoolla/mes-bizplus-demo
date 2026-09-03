package com.bizplus.mes.domain.defect.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefectItemCreateDto {

    private Long typeId;

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private String description;
    private String remark;
}

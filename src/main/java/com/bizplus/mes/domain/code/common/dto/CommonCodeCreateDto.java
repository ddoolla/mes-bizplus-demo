package com.bizplus.mes.domain.code.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonCodeCreateDto {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String description;
}

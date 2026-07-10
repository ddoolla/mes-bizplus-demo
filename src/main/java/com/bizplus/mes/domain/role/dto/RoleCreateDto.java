package com.bizplus.mes.domain.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleCreateDto {

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private String description;
}

package com.bizplus.mes.domain.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleUpdateDto {

    @NotBlank
    private String name;
    private String description;
}

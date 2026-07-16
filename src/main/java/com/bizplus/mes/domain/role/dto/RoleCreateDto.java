package com.bizplus.mes.domain.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RoleCreateDto {

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private String description;

    private List<Long> permissionIds;
}

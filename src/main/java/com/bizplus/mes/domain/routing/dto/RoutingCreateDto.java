package com.bizplus.mes.domain.routing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoutingCreateDto {

    @NotNull
    private Long itemId;

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private String version;
    private Boolean isDefault;
    private String description;
}

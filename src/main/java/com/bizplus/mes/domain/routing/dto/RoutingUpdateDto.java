package com.bizplus.mes.domain.routing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RoutingUpdateDto {

    @NotBlank
    private final String code;

    @NotBlank
    private final String name;
    private final String version;
    private final boolean primary;
    private final String description;

    public RoutingUpdateDto(String code,
                            String name,
                            String version,
                            Boolean primary,
                            String description) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = Boolean.TRUE.equals(primary);
        this.description = description;
    }
}

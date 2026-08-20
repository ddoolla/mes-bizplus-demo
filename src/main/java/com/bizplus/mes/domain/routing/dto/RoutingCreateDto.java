package com.bizplus.mes.domain.routing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RoutingCreateDto {

    @NotNull
    private final Long itemId;

    @NotBlank
    private final String code;

    @NotBlank
    private final String name;
    private final String version;
    private final boolean primary;
    private final String description;

    public RoutingCreateDto(Long itemId,
                            String code,
                            String name,
                            String version,
                            Boolean primary,
                            String description) {
        this.itemId = itemId;
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = Boolean.TRUE.equals(primary);
        this.description = description;
    }
}

package com.bizplus.mes.domain.routing.dto;

import com.bizplus.mes.domain.routing.process.dto.RoutingProcessUpdateDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class RoutingUpdateDto {

    @NotBlank
    private final String code;

    @NotBlank
    private final String name;
    private final String version;
    private final Boolean isDefault;
    private final String description;
    private final List<RoutingProcessUpdateDto> routingProcesses;

    public RoutingUpdateDto(String code,
                            String name,
                            String version,
                            Boolean isDefault,
                            String description,
                            List<RoutingProcessUpdateDto> routingProcesses) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.isDefault = isDefault;
        this.description = description;
        this.routingProcesses = routingProcesses == null ? List.of() : routingProcesses;
    }
}

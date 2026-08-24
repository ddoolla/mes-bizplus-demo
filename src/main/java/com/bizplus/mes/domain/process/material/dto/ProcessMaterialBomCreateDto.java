package com.bizplus.mes.domain.process.material.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class ProcessMaterialBomCreateDto {

    @NotEmpty
    private final List<Long> bomIds;

    public ProcessMaterialBomCreateDto(List<Long> bomIds) {
        this.bomIds = bomIds == null ? List.of() : bomIds;
    }
}

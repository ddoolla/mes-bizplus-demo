package com.bizplus.mes.domain.process.material.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class ProcessMaterialBomCreateDto {

    @NotEmpty
    private final List<Long> bomItemIds;

    public ProcessMaterialBomCreateDto(List<Long> bomItemIds) {
        this.bomItemIds = bomItemIds == null ? List.of() : bomItemIds;
    }
}

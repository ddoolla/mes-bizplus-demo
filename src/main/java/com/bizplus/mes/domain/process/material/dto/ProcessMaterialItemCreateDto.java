package com.bizplus.mes.domain.process.material.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class ProcessMaterialItemCreateDto {

    @NotEmpty
    private final List<Long> itemIds;

    public ProcessMaterialItemCreateDto(List<Long> itemIds) {
        this.itemIds = itemIds == null ? List.of() : itemIds;
    }
}

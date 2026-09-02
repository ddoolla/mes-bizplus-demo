package com.bizplus.mes.domain.inspection.spec.item.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InspectionSpecItemCreateDto {

    @NotEmpty
    private List<Long> inspectionItemIds;
}

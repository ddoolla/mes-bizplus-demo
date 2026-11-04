package com.bizplus.mes.domain.routing.process.dto;

import com.bizplus.mes.domain.process.material.dto.ProcessMaterialUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RoutingProcessUpdateDto {

    private Long inspectionSpecId;
    private boolean processInspectionRequired;
    private List<ProcessMaterialUpdateDto> processMaterials;
}

package com.bizplus.mes.domain.process.material.dto;

import com.bizplus.mes.domain.uom.conversion.dto.ConvertibleUomDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProcessMaterialEditDto {

    private ProcessMaterialDto processMaterial;
    private List<ConvertibleUomDto> convertibleUoms;
}

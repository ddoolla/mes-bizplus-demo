package com.bizplus.mes.domain.uom.conversion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UomConversionSearchDto {

    private Long fromUomId;
    private Long toUomId;
}

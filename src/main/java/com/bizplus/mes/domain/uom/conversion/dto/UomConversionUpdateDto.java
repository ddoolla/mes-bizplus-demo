package com.bizplus.mes.domain.uom.conversion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class UomConversionUpdateDto {

    private BigDecimal factor;
}

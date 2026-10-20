package com.bizplus.mes.domain.material.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MaterialInputLotDto {

    private Long id;
    private String no;
    private Long itemId;
    private BigDecimal quantity;
    private String inputUomCode;
}

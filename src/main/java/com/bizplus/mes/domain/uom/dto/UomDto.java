package com.bizplus.mes.domain.uom.dto;

import com.bizplus.mes.domain.uom.UomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UomDto {

    private Long id;
    private String code;
    private String name;
    private UomType type;
    private Integer decimalPlaces;
}

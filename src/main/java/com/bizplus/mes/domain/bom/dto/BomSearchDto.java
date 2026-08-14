package com.bizplus.mes.domain.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BomSearchDto {

    private String itemCode;
    private String itemName;
    private String code;
    private String name;
}

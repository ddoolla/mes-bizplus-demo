package com.bizplus.mes.domain.production.order.process.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductionOrderProcessDto {

    private Long id;
    private String code;
    private String name;
    private int stepNo;
}

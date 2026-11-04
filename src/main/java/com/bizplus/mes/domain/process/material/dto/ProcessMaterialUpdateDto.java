package com.bizplus.mes.domain.process.material.dto;

import com.bizplus.mes.domain.process.material.ConsumptionMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProcessMaterialUpdateDto {

    private Long id;
    private Long uomId;
    private BigDecimal quantity;
    private ConsumptionMethod consumptionMethod;
}

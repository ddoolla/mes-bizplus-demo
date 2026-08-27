package com.bizplus.mes.domain.process.material.dto;

import com.bizplus.mes.domain.process.material.ConsumptionMethod;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProcessMaterialUpdateDto {

    @NotEmpty
    private List<EditParams> processMaterials;

    public record EditParams(
            Long id,
            Long consumptionUomId,
            BigDecimal quantity,
            ConsumptionMethod consumptionMethod
    ) {
    }
}

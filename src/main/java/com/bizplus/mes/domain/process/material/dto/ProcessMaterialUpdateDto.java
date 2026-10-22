package com.bizplus.mes.domain.process.material.dto;

import com.bizplus.mes.domain.process.material.ConsumptionMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProcessMaterialUpdateDto {

    @NotEmpty
    private List<@Valid EditParams> processMaterials;

    public record EditParams(
            @NotNull Long id,
            @NotNull Long uomId,
            @NotNull BigDecimal quantity,
            @NotNull ConsumptionMethod consumptionMethod
    ) {
    }
}

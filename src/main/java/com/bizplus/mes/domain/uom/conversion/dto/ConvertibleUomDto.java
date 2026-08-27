package com.bizplus.mes.domain.uom.conversion.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ConvertibleUomDto {

    private final Long id;
    private final BigDecimal factor;
    private final FromUomInfo fromUom;

    @QueryProjection
    public ConvertibleUomDto(Long id,
                             BigDecimal factor,
                             Long fromUomId,
                             String fromUomCode,
                             String fromUomName,
                             Integer fromUomScale) {
        this.id = id;
        this.factor = factor;
        this.fromUom = new FromUomInfo(
                fromUomId,
                fromUomCode,
                fromUomName,
                fromUomScale
        );
    }

    public record FromUomInfo(
            Long id,
            String code,
            String name,
            Integer scale
    ) {
    }
}

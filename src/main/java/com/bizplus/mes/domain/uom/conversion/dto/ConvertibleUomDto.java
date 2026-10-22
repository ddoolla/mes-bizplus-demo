package com.bizplus.mes.domain.uom.conversion.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ConvertibleUomDto {

    private final Long id;
    private final String code;
    private final String name;
    private final Integer scale;
    private final BigDecimal factor;

    @QueryProjection
    public ConvertibleUomDto(Long fromUomId,
                             String fromUomCode,
                             String fromUomName,
                             Integer fromUomScale,
                             BigDecimal factor) {
        this.id = fromUomId;
        this.code = fromUomCode;
        this.name = fromUomName;
        this.scale = fromUomScale;
        this.factor = factor;
    }
}

package com.bizplus.mes.domain.uom.conversion.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UomConversionDto {

    private final Long id;
    private final BigDecimal factor;
    private final FromUomInfo fromUom;
    private final ToUomInfo toUom;

    @QueryProjection
    public UomConversionDto(Long id,
                            BigDecimal factor,
                            Long fromUomId,
                            String fromUomCode,
                            String fromUomName,
                            Long toUomId,
                            String toUomCode,
                            String toUomName) {
        this.id = id;
        this.factor = factor;
        this.fromUom = new FromUomInfo(fromUomId, fromUomCode, fromUomName);
        this.toUom = new ToUomInfo(toUomId, toUomCode, toUomName);
    }

    public record FromUomInfo(Long id, String code, String name) {
    }

    public record ToUomInfo(Long id, String code, String name) {
    }
}

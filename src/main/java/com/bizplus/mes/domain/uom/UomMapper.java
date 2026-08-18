package com.bizplus.mes.domain.uom;

import com.bizplus.mes.domain.uom.dto.UomCreateDto;
import com.bizplus.mes.domain.uom.dto.UomDto;
import com.bizplus.mes.domain.uom.dto.UomUpdateDto;

public class UomMapper {

    public static UomDto toDto(Uom uom) {

        return new UomDto(
                uom.getId(),
                uom.getCode(),
                uom.getName(),
                uom.getType(),
                uom.getScale()
        );
    }

    public static Uom toEntity(UomCreateDto dto) {

        return new Uom(
                dto.getCode(),
                dto.getName(),
                dto.getType(),
                dto.getScale()
        );
    }

    public static void apply(Uom uom, UomUpdateDto dto) {

        uom.update(
                dto.getCode(),
                dto.getName(),
                dto.getType(),
                dto.getScale()
        );
    }
}

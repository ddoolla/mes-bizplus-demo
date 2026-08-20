package com.bizplus.mes.domain.bom;

import com.bizplus.mes.domain.bom.dto.BomCreateDto;
import com.bizplus.mes.domain.bom.dto.BomUpdateDto;
import com.bizplus.mes.domain.item.Item;

public class BomMapper {

    public static Bom toEntity(Item item, BomCreateDto dto) {
        return new Bom(
                item,
                dto.getCode(),
                dto.getName(),
                dto.getVersion(),
                dto.isPrimary(),
                dto.getRemark()
        );
    }

    public static void apply(Bom bom, BomUpdateDto dto) {
        bom.update(
                dto.getCode(),
                dto.getName(),
                dto.getVersion(),
                dto.isPrimary(),
                dto.getRemark()
        );
    }
}

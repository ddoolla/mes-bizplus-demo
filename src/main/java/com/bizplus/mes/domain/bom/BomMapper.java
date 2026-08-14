package com.bizplus.mes.domain.bom;

import com.bizplus.mes.domain.bom.dto.BomCreateDto;
import com.bizplus.mes.domain.bom.dto.BomUpdateDto;
import com.bizplus.mes.domain.item.Item;

public class BomMapper {

    public static Bom toEntity(Item item, int nextRevisionNo, BomCreateDto dto) {
        return new Bom(
                item,
                dto.getCode(),
                dto.getName(),
                nextRevisionNo,
                dto.getRemark()
        );
    }

    public static void apply(Bom bom, Item item, BomUpdateDto dto) {
        bom.update(
                item,
                dto.getCode(),
                dto.getName(),
                dto.getRemark()
        );
    }
}
